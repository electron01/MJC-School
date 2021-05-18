package by.epam.esm.constant;

/**
 * class WebConstant
 * class contains the necessary constants such as the request type and names
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public class WebConstant {

    private WebConstant(){
    }
    public static final String ORDERS = "Orders";
    public static final String PAGE_DEFAULT_VALUE = "1";
    public static final String LIMIT_DEFAULT_VALUE = "6";

    public static final String NEXT_PAGE = "nextPage";
    public static final String PREVIOUS_PAGE = "previousPage";
    public static final String FIRST_PAGE = "firstPage";
    public static final String LAST_PAGE = "lastPage";
    public static final String DELETE = "delete";
    public static final String DELETE_METHOD = "DELETE";
    public static final String UPDATE = "update";
    public static final String UPDATE_METHOD = "PUT";
    public static final String PART_UPDATE = "partUpdate";
    public static final String PART_UPDATE_METHOD = "PATCH";
}
