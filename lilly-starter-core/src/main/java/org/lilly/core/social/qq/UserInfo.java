package org.lilly.core.social.qq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)   //第三方服务中返回字段如果有变 忽略解析
public class UserInfo {

    /**
     * 返回码
     */
    private Integer ret;

    /**
     * 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
     */
    private String msg;

    /**
     * 用户在QQ空间的昵称。
     */
    private String nickname;

    /**
     * 大小为30×30像素的QQ空间头像URL。
     */

    private String figureurl;

    /**
     * 大小为50×50像素的QQ空间头像URL。
     */

    private String figureurl_1;

    /**
     * 大小为100×100像素的QQ空间头像URL。
     */
    private String figureurl_2;

    /**
     * 大小为40×40像素的QQ头像URL。
     */
    private String figureurl_qq_1;

    /**
     * 大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有。
     */
    private String figureurl_qq_2;

    /**
     * 性别。 如果获取不到则默认返回"男"
     */
    private String gender;

    /**
     *
     */
    private String 	is_lost;

    private String gender_type;

    private String province;

    private String city;

    private String year;

    private String constellation;

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getGender_type() {
        return gender_type;
    }

    public void setGender_type(String gender_type) {
        this.gender_type = gender_type;
    }

    private String openId;

    public String getIs_lost() {
        return is_lost;
    }

    public void setIs_lost(String is_lost) {
        this.is_lost = is_lost;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getRet() {
        return ret;
    }

    public void setRet(Integer ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }

    public String getFigureurl_1() {
        return figureurl_1;
    }

    public void setFigureurl_1(String figureurl_1) {
        this.figureurl_1 = figureurl_1;
    }

    public String getFigureurl_2() {
        return figureurl_2;
    }

    public void setFigureurl_2(String figureurl_2) {
        this.figureurl_2 = figureurl_2;
    }

    public String getFigureurl_qq_1() {
        return figureurl_qq_1;
    }

    public void setFigureurl_qq_1(String figureurl_qq_1) {
        this.figureurl_qq_1 = figureurl_qq_1;
    }

    public String getFigureurl_qq_2() {
        return figureurl_qq_2;
    }

    public void setFigureurl_qq_2(String figureurl_qq_2) {
        this.figureurl_qq_2 = figureurl_qq_2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 返回示例
     {
     "ret": 0,
     "msg": "",
     "is_lost": 0,
     "nickname": "『路人甲』",
     "gender": "男",
     "gender_type": 1,
     "province": "四川",
     "city": "乐山",
     "year": "1994",
     "constellation": "",
     "figureurl": "http:\/\/qzapp.qlogo.cn\/qzapp\/101893703\/DB26DADE0C52721C576BC1169530EC64\/30",
     "figureurl_1": "http:\/\/qzapp.qlogo.cn\/qzapp\/101893703\/DB26DADE0C52721C576BC1169530EC64\/50",
     "figureurl_2": "http:\/\/qzapp.qlogo.cn\/qzapp\/101893703\/DB26DADE0C52721C576BC1169530EC64\/100",
     "figureurl_qq_1": "http://thirdqq.qlogo.cn/g?b=oidb&k=Q6If2wA10wKMbwCYueQqxg&s=40&t=1593445861",
     "figureurl_qq_2": "http://thirdqq.qlogo.cn/g?b=oidb&k=Q6If2wA10wKMbwCYueQqxg&s=100&t=1593445861",
     "figureurl_qq": "http://thirdqq.qlogo.cn/g?b=oidb&k=Q6If2wA10wKMbwCYueQqxg&s=640&t=1593445861",
     "figureurl_type": "1",
     "is_yellow_vip": "0",
     "vip": "0",
     "yellow_vip_level": "0",
     "level": "0",
     "is_yellow_year_vip": "0"
     }
     */
}
