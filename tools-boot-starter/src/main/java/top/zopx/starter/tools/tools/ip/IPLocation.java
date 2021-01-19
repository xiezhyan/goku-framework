package top.zopx.starter.tools.tools.ip;

public class IPLocation {

    private String country;
    private String area;

    public IPLocation() {
        country = area = "";
    }

    public IPLocation getCopy() {
        IPLocation ret = new IPLocation();
        ret.country = country;
        ret.area = area;
        return ret;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        //如果为局域网，纯真IP地址库的地区会显示CZ88.NET,这里把它去掉
        if("CZ88.NET".equals(area.trim())){
            this.area="本机或本网络";
        }else{
            this.area = area;
        }
    }
}
