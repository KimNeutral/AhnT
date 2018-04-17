package dgsw.hs.kr.ahnt.Model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Created by neutral on 02/04/2018.
 */

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User {
    private ClassInfo myClass;

    private String email;

    private String name;

    private String gender;

    private String mobile;

    public ClassInfo getMyClass() {
        return myClass;
    }

    public void setMyClass(ClassInfo myClass) {
        this.myClass = myClass;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "ClassPojo [myClass = " + myClass + ", email = " + email + ", name = " + name + ", gender = " + gender + ", mobile = " + mobile + "]";
    }

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public class ClassInfo{
        private String classRoom;

        private String grade;

        private String classNumber;

        public String getClassRoom() {

            return classRoom;
        }

        public void setClassRoom(String classRoom) {

            this.classRoom = classRoom;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getClassNumber() {
            return classNumber;
        }

        public void setClassNumber(String classNumber) {
            this.classNumber = classNumber;
        }

        @Override
        public String toString() {
            return "ClassPojo [classroom = " + classRoom
                    + ", grade = " + grade + ", class_number = " + classNumber + "]";
        }
    }
}
