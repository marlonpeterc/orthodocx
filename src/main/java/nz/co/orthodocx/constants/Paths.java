package nz.co.orthodocx.constants;

public enum Paths {

    WEBFLUX_PROFILE("/webflux/profile/"),
    FIRSTNAME("firstname"),
    LASTNAME("lastname"),
    ID("id"),
    FIRSTNAME_PATH_PARAM("{firstname}"),
    LASTNAME_PATH_PARAM("{lastname}"),
    ID_PATH_PARAM("{id}"),
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
