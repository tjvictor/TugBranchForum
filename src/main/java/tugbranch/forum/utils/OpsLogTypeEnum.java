package tugbranch.forum.utils;

public enum OpsLogTypeEnum {

    Login_out("登录/退出", 1),
    ResetPassword("修改密码", 2),
    CompanyMgt("公司管理", 100),
    StaffMgt("人员管理", 101),
    RegisterMgt("注册管理", 102),
    NotificationMgt("通知管理", 103),
    DocumentMgt("资料管理", 104),
    ComponentMgt("备件管理", 200),
    TugMgt("拖轮管理", 201),
    ShipMgt("大船管理", 202),
    LoanMgt("借出管理", 203),
    BorrowMgt("借入管理", 204),
    MeetingMgt("会议管理", 300),
    ProdReportMgt("报表管理", 400),
    ProdReportCheckMgt("报表审核管理", 401),
    ProdReportAvgMgt("平均报表管理", 402);

    private String name;
    private int index;
    private OpsLogTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    public static String getName(int index) {
        for (OpsLogTypeEnum c : OpsLogTypeEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
}
