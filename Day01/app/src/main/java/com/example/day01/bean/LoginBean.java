package com.example.day01.bean;

import java.util.List;

public class LoginBean {

    /**
     * code : 200
     * ret : 登录成功
     * data : [{"id":223,"uid":"220","name":"zhangsan","password":"123456","age":18,"sex":1,"phone":"13400000000","header":"http://yun918.cn/study/public/uploadfiles/image/538926a6e0d4c8c8bb2f49419e0c0f8a.png"}]
     */

    private int code;
    private String ret;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 223
         * uid : 220
         * name : zhangsan
         * password : 123456
         * age : 18
         * sex : 1
         * phone : 13400000000
         * header : http://yun918.cn/study/public/uploadfiles/image/538926a6e0d4c8c8bb2f49419e0c0f8a.png
         */

        private int id;
        private String uid;
        private String name;
        private String password;
        private int age;
        private int sex;
        private String phone;
        private String header;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }
    }
}
