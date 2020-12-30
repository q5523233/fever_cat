package com.nepo.fevercat.ui.mine.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

/**
 * Created by sm on 2019/3/25.
 * 运动量化表病人列表
 */
@Entity
public class MotionBean implements Serializable{

    private static final long serialVersionUID = 7505578371251096147L;
    @Property
    public String name;
    @Property
    public String age;
    @Property
    public String sex;
    @Property
    public String diagnosis;
    @Id(autoincrement = true)
    public Long id;
    @Property
    public int type;//区分 0：运动量化表 1：基础生命信息量化表数据
    @Generated(hash = 47908508)
    public MotionBean(String name, String age, String sex, String diagnosis, Long id, int type) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.diagnosis = diagnosis;
        this.id = id;
        this.type = type;
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MotionBean)) {
            return false;
        } else {
            return ((MotionBean) obj).id ==( this.id )&& ((MotionBean) obj).name.equals(((MotionBean) obj).name);
        }
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getDiagnosis() {
        return this.diagnosis;
    }
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }


    @Generated(hash = 607951342)
    public MotionBean() {
    }

}
