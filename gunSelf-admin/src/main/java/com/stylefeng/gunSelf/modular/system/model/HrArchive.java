package com.stylefeng.gunSelf.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylezhang123
 * @since 2018-07-08
 */
@TableName("hr_archive")
public class HrArchive extends Model<HrArchive> {

    private static final long serialVersionUID = 1L;

    /**
     * 档案ID
     */
    @TableId(value = "ARCHIVE_ID", type = IdType.AUTO)
    private Integer archiveId;
    /**
     * 薪资方案ID
     */
    @TableField("SCHEMA_ID")
    private Integer schemaId;
    /**
     * 对应账号
     */
    @TableField("STAFF_ID")
    private Integer staffId;
    /**
     * 工号
     */
    @TableField("EMPLOYEE_NO")
    private String employeeNo;
    /**
     * 姓名
     */
    @TableField("NAME")
    private String name;
    /**
     * 性别
     */
    @TableField("SEX")
    private Integer sex;
    /**
     * 部门
     */
    @TableField("DEPARTMENT_ID")
    private Integer departmentId;
    /**
     * 职务
     */
    @TableField("POSITION")
    private String position;
    /**
     * 职级
     */
    @TableField("POSITION_RANK")
    private Integer positionRank;
    /**
     * 职等
     */
    @TableField("POSITION_GRADE")
    private Integer positionGrade;
    /**
     * 行政级别
     */
    @TableField("EXECUTIVE_LEVEL")
    private Integer executiveLevel;
    /**
     * 出生日期
     */
    @TableField("BIRTH_DATE")
    private String birthDate;
    /**
     * 年龄
     */
    @TableField("AGE")
    private Integer age;
    /**
     * 身份证号
     */
    @TableField("ID_CARD")
    private String idCard;
    /**
     * 籍贯
     */
    @TableField("ORIGO")
    private String origo;
    /**
     * 户口所在
     */
    @TableField("RESIDENCE_REGISTRATION")
    private String residenceRegistration;
    /**
     * 民族
     */
    @TableField("NATIONALITY")
    private Integer nationality;
    /**
     * 政治面貌
     */
    @TableField("POLITICAL_STATUS")
    private Integer politicalStatus;
    /**
     * 婚姻状况
     */
    @TableField("MARRIAGE_STATUS")
    private Integer marriageStatus;
    /**
     * 毕业院校
     */
    @TableField("GRADUATE_SCHOOL")
    private String graduateSchool;
    /**
     * 身份证到期
     */
    @TableField("IDCARD_EXPIRY_DATE")
    private String idcardExpiryDate;
    /**
     * 专业
     */
    @TableField("SPECIALTY")
    private String specialty;
    /**
     * 学历
     */
    @TableField("EDU_QUALIFICATION")
    private Integer eduQualification;
    /**
     * 学位
     */
    @TableField("EDU_DEGREE")
    private Integer eduDegree;
    /**
     * 外语水平
     */
    @TableField("FOREIGN_LANGUAGE_LEVEL")
    private Integer foreignLanguageLevel;
    /**
     * 电子邮箱
     */
    @TableField("EMAIL")
    private String email;
    /**
     * 家庭地址
     */
    @TableField("HOME_ADDRESS")
    private String homeAddress;
    /**
     * 移动电话
     */
    @TableField("MOBILE")
    private String mobile;
    /**
     * 用工类型
     */
    @TableField("EMPLOYMENT_TYPE")
    private Integer employmentType;
    /**
     * 入职时间
     */
    @TableField("ENTRY_DATE")
    private String entryDate;
    /**
     * 转正日期
     */
    @TableField("REGULAR_DATE")
    private String regularDate;
    /**
     * 教育经历
     */
    @TableField("EDU_EXPERIENCE")
    private String eduExperience;
    /**
     * 个人简历
     */
    @TableField("RESUME")
    private String resume;
    /**
     * 档案状态
     */
    @TableField("ARCHIVE_STATUS")
    private Integer archiveStatus;
    /**
     * 离职类别
     */
    @TableField("LEAVE_TYPE")
    private Integer leaveType;
    /**
     * 离职日期
     */
    @TableField("LEAVE_DATE")
    private String leaveDate;
    /**
     * 离职原因
     */
    @TableField("LEAVE_REASON")
    private String leaveReason;
    /**
     * 是否删除
     */
    @TableField("IS_DEL")
    private Integer isDel;
    /**
     * 删除时间
     */
    @TableField("DEL_TIME")
    private String delTime;
    /**
     * 删除人
     */
    @TableField("DEL_OPERATOR")
    private Integer delOperator;
    /**
     * 操作人
     */
    @TableField("OPERATOR")
    private Integer operator;
    /**
     * 操作日期
     */
    @TableField("OPERATE_TIME")
    private String operateTime;
    /**
     * 单位ID
     */
    @TableField("DOMAIN_ID")
    private Integer domainId;
    /**
     * 备注
     */
    @TableField("DESC_CNT")
    private String descCnt;


    public Integer getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(Integer archiveId) {
        this.archiveId = archiveId;
    }

    public Integer getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Integer schemaId) {
        this.schemaId = schemaId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getPositionRank() {
        return positionRank;
    }

    public void setPositionRank(Integer positionRank) {
        this.positionRank = positionRank;
    }

    public Integer getPositionGrade() {
        return positionGrade;
    }

    public void setPositionGrade(Integer positionGrade) {
        this.positionGrade = positionGrade;
    }

    public Integer getExecutiveLevel() {
        return executiveLevel;
    }

    public void setExecutiveLevel(Integer executiveLevel) {
        this.executiveLevel = executiveLevel;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getOrigo() {
        return origo;
    }

    public void setOrigo(String origo) {
        this.origo = origo;
    }

    public String getResidenceRegistration() {
        return residenceRegistration;
    }

    public void setResidenceRegistration(String residenceRegistration) {
        this.residenceRegistration = residenceRegistration;
    }

    public Integer getNationality() {
        return nationality;
    }

    public void setNationality(Integer nationality) {
        this.nationality = nationality;
    }

    public Integer getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(Integer politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public Integer getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(Integer marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }

    public String getIdcardExpiryDate() {
        return idcardExpiryDate;
    }

    public void setIdcardExpiryDate(String idcardExpiryDate) {
        this.idcardExpiryDate = idcardExpiryDate;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Integer getEduQualification() {
        return eduQualification;
    }

    public void setEduQualification(Integer eduQualification) {
        this.eduQualification = eduQualification;
    }

    public Integer getEduDegree() {
        return eduDegree;
    }

    public void setEduDegree(Integer eduDegree) {
        this.eduDegree = eduDegree;
    }

    public Integer getForeignLanguageLevel() {
        return foreignLanguageLevel;
    }

    public void setForeignLanguageLevel(Integer foreignLanguageLevel) {
        this.foreignLanguageLevel = foreignLanguageLevel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(Integer employmentType) {
        this.employmentType = employmentType;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getRegularDate() {
        return regularDate;
    }

    public void setRegularDate(String regularDate) {
        this.regularDate = regularDate;
    }

    public String getEduExperience() {
        return eduExperience;
    }

    public void setEduExperience(String eduExperience) {
        this.eduExperience = eduExperience;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Integer getArchiveStatus() {
        return archiveStatus;
    }

    public void setArchiveStatus(Integer archiveStatus) {
        this.archiveStatus = archiveStatus;
    }

    public Integer getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(Integer leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getDelTime() {
        return delTime;
    }

    public void setDelTime(String delTime) {
        this.delTime = delTime;
    }

    public Integer getDelOperator() {
        return delOperator;
    }

    public void setDelOperator(Integer delOperator) {
        this.delOperator = delOperator;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    public String getDescCnt() {
        return descCnt;
    }

    public void setDescCnt(String descCnt) {
        this.descCnt = descCnt;
    }

    @Override
    protected Serializable pkVal() {
        return this.archiveId;
    }

    @Override
    public String toString() {
        return "HrArchive{" +
        "archiveId=" + archiveId +
        ", schemaId=" + schemaId +
        ", staffId=" + staffId +
        ", employeeNo=" + employeeNo +
        ", name=" + name +
        ", sex=" + sex +
        ", departmentId=" + departmentId +
        ", position=" + position +
        ", positionRank=" + positionRank +
        ", positionGrade=" + positionGrade +
        ", executiveLevel=" + executiveLevel +
        ", birthDate=" + birthDate +
        ", age=" + age +
        ", idCard=" + idCard +
        ", origo=" + origo +
        ", residenceRegistration=" + residenceRegistration +
        ", nationality=" + nationality +
        ", politicalStatus=" + politicalStatus +
        ", marriageStatus=" + marriageStatus +
        ", graduateSchool=" + graduateSchool +
        ", idcardExpiryDate=" + idcardExpiryDate +
        ", specialty=" + specialty +
        ", eduQualification=" + eduQualification +
        ", eduDegree=" + eduDegree +
        ", foreignLanguageLevel=" + foreignLanguageLevel +
        ", email=" + email +
        ", homeAddress=" + homeAddress +
        ", mobile=" + mobile +
        ", employmentType=" + employmentType +
        ", entryDate=" + entryDate +
        ", regularDate=" + regularDate +
        ", eduExperience=" + eduExperience +
        ", resume=" + resume +
        ", archiveStatus=" + archiveStatus +
        ", leaveType=" + leaveType +
        ", leaveDate=" + leaveDate +
        ", leaveReason=" + leaveReason +
        ", isDel=" + isDel +
        ", delTime=" + delTime +
        ", delOperator=" + delOperator +
        ", operator=" + operator +
        ", operateTime=" + operateTime +
        ", domainId=" + domainId +
        ", descCnt=" + descCnt +
        "}";
    }
}
