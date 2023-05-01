import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

/**
 * 智能合约类
 *
 * @author Wangrihao
 * @date 2023/04/14
 */

@DataType
public class QueryResult {
    @Property
    private String sno;

    @Property
    private String sname;

    @Property
    private String sex;

    @Property
    private String age;

    @Property
    private String college;

    public QueryResult(String sno, String sname, String sex, String age, String college) {
        this.sno = sno;
        this.sname = sname;
        this.sex = sex;
        this.age = age;
        this.college = college;
    }

    public QueryResult() {
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
