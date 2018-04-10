package dgsw.hs.kr.ahnt.Model;

/**
 * Created by neutral on 02/04/2018.
 */

public class User {
    private ClassInfo my_class;

    private String email;

    private String name;

    private String gender;

    private String auth;

    private String mobile;

    public ClassInfo getMy_class() {
        return my_class;
    }

    public void setMy_class(ClassInfo my_class) {
        this.my_class = my_class;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "ClassPojo [my_class = " + my_class + ", email = " + email + ", name = " + name + ", gender = " + gender + ", auth = " + auth + ", mobile = " + mobile + "]";
    }

    public class ClassInfo{
        private String classroom;

        private String grade;

        private String class_number;

        public String getClassroom() {

            return classroom;
        }

        public void setClass(String classroom) {

            this.classroom = classroom;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getClass_number() {
            return class_number;
        }

        public void setClass_number(String class_number) {
            this.class_number = class_number;
        }

        @Override
        public String toString() {
            return "ClassPojo [class = " + classroom
                    + ", grade = " + grade + ", class_number = " + class_number + "]";
        }
    }
}
