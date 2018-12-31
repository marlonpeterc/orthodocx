package nz.co.orthodocx.constants;

public enum Paths {

    WEBFLUX_PROFILE("/webflux/profile/"),
    FIRSTNAME("firstname"),
    LASTNAME("lastname"),
    FIRSTNAME_PATH_PARAM("{firstname}"),
    LASTNAME_PATH_PARAM("{lastname}"),
    SEPARATOR("/"),
    ALL("all"),
    OR("or");

    private String _value;

    Paths(String value){
        _value = value;
    }

    public String value(){
        return _value;
    }
}
