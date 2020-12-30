package com.nepo.fevercat.ui.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.ArrayTableData;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.utils.DensityUtils;
import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.app.BaseApplication;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.common.utils.GreenDaoUtils;
import com.nepo.fevercat.common.utils.ToastUtils;
import com.nepo.fevercat.common.widget.load.LoadDialog;
import com.nepo.fevercat.event.ExcelUpdateEvent;
import com.nepo.fevercat.ui.mine.bean.MotionBean;
import com.nepo.fevercat.ui.mine.excel.ExcelCallback;
import com.nepo.fevercat.ui.mine.excel.POIExcel2Table;
import com.nepo.fevercat.ui.mine.utils.WriteBaseInfoUtil;
import com.nepo.fevercat.ui.mine.utils.WriteExcelUtils2;

import org.apache.poi.ss.usermodel.Cell;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;


import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 通过poi来填充表格
 * 暂时只能支持xls
 */
public class POIExcelModeActivity
        extends BaseActivity
        implements ExcelCallback {

    @BindView(R.id.table)
    SmartTable table;
    @BindView(R.id.edit_query)
    EditText mEditText;
    @BindView(R.id.tv_query)
    Button mBtConfirm;
    @BindView(R.id.toolbar_excel)
    Toolbar toolbarExcel;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private String filepath = "a.xls";
    private POIExcel2Table iExcel2Table;
    private Cell mClickCell;
    private MotionBean motionBean;
    private LoadDialog loadDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_excel_table;
    }

    @Override
    public void initPresenter() {
        handleIntent();
    }

    @Override
    public void initView() {
        setSupportActionBar(toolbarExcel);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this, 15));
        iExcel2Table = new POIExcel2Table();
        filepath = getExcelPath(motionBean);
        iExcel2Table.setIsAssetsFile(false);
        if (TextUtils.isEmpty(filepath)) {
            if (motionBean.name != null) {
                ToastUtils.showToast("当前文档数据已损坏，数据需要重新填写");
            }
            filepath = motionBean.type == 0 ? "a.xls" : "b.xls";
            iExcel2Table.setIsAssetsFile(true);
        }
        iExcel2Table.initTableConfig(this, table);
        iExcel2Table.setCallback(this);
        iExcel2Table.loadSheetList(this, filepath);
        iExcel2Table.setOnCellClickListener(new TableData.OnRowClickListener() {
            @Override
            public void onClick(Column column, Object o, int col, int row) {
                mClickCell = (Cell) column.getDatas()
                        .get(row);
            }
        });
        mBtConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickCell == null) {
                    Toast.makeText(POIExcelModeActivity.this, "请先选中要修改的单元格", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                mClickCell.setCellValue(mEditText.getText().toString());
                table.notifyDataChanged();
                mEditText.getText().clear();
            }
        });
    }

    private String getExcelPath(MotionBean motionBean) {
        File file = new File(WriteExcelUtils2.getFile(this, motionBean.getName() + "_" + motionBean.id + "_" + motionBean.type));
        if (file != null && file.exists())
            return file.getAbsolutePath();
        else
            return null;
    }


    @Override
    protected void onDestroy() {

        if (iExcel2Table != null) {
            iExcel2Table.clear();
        }
        iExcel2Table = null;
        super.onDestroy();
    }

    @Override
    public void getSheetListSuc(List<String> sheetNames) {
        iExcel2Table.loadSheetContent(POIExcelModeActivity.this, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excel_opration, menu);
        menu.findItem(R.id.menu_edit).setTitle("保存");
        menu.findItem(R.id.menu_save).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_edit:
                saveTable();
                break;
            case R.id.menu_save:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exportTable() {
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(AppConstant.kEY_MOTION_LIST)) {
            Object o = intent.getSerializableExtra(AppConstant.kEY_MOTION_LIST);
            if (o instanceof MotionBean) {
                this.motionBean = (MotionBean) o;
            }
        } else {
            motionBean = new MotionBean();
        }
        tvTitle.setText(motionBean.type == 0 ? getString(R.string.motion_quantization_table) : getString(R.string.base_info_excel));
    }

    private void saveTable() {
        final Cell[][] cells = (Cell[][]) ArrayTableData.transformColumnArray(((ArrayTableData) table.getTableData()).getData());
        if (!checkTable(cells)) {
            return;
        }
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                GreenDaoUtils.getInstance(BaseApplication.getAppContext()).addMotionBean(motionBean);
                if (motionBean.type == 0) {
                    WriteExcelUtils2.getInstance().writeExecleToFile(POIExcelModeActivity.this, cells, motionBean);
                } else {
                    WriteBaseInfoUtil.getInstance().writeExecleToFile(POIExcelModeActivity.this, cells, motionBean);
                }
                String msg = WriteExcelUtils2.getFile(POIExcelModeActivity.this, motionBean.getName() + "_" + motionBean.id + "_" + motionBean.type);
                subscriber.onNext(msg);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<String>() {
            @Override
            public void onStart() {
                if (loadDialog == null)
                    loadDialog = new LoadDialog(POIExcelModeActivity.this);
                loadDialog.show();
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showToast("操作失败");
                loadDialog.dismiss();
            }

            @Override
            public void onNext(String path) {
                loadDialog.dismiss();
                new MaterialDialog.Builder(POIExcelModeActivity.this).title("提示")
                        .content("文件已保存在" + path)
                        .positiveText("确定")
                        .build().show();
                EventBus.getDefault().post(new ExcelUpdateEvent());
            }
        });

    }

    /**
     * 校验字段合法性
     *
     * @param cells
     */
    private boolean checkTable(Cell[][] cells) {
        if (motionBean.type == 0) {
            motionBean.name = cells[1][1].getStringCellValue();
            motionBean.age = cells[1][4].getStringCellValue();
            motionBean.diagnosis = cells[1][6].getStringCellValue();
            motionBean.sex = cells[1][18].getStringCellValue();

        } else {
            motionBean.name = cells[0][10].getStringCellValue();
            motionBean.age = cells[0][3].getStringCellValue();
            motionBean.diagnosis = cells[0][21].getStringCellValue();
            motionBean.sex = cells[0][6].getStringCellValue();
        }
        if (TextUtils.isEmpty(motionBean.name)) {
            ToastUtils.showToast("姓名不能为空");
            return false;
        }
        try {
            if (!TextUtils.isEmpty(motionBean.age)) {
                int age = Integer.parseInt(motionBean.age);
                if (age < 0 || age > 120) {
                    ToastUtils.showToast("填写的年龄数值不在合法范围内");
                    return false;
                }
            }
        } catch (Exception e) {
            ToastUtils.showToast("年龄格式不合法");
            return false;
        }
        return true;
    }

    public static void newIntent(Context context, MotionBean bean) {
        Intent intent = new Intent(context, POIExcelModeActivity.class);
        intent.putExtra(AppConstant.kEY_MOTION_LIST, bean);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}
