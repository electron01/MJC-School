package by.epam.esm.constant;

/**
 * class RepoConstant
 * class contains the necessary constants such as the path to files and queries to the database
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public final class RepoConstant {
    private RepoConstant() {

    }
    //Request
    public static String MOST_WIDELY_USED_TAG ="SELECT tag.id,tag.name,tag.remove FROM orders\n" +
            "JOIN certificates_orders ON orders.id = certificates_orders.order_id\n" +
            "JOIN gift_certificate ON certificates_orders.gift_certificate_id = gift_certificate.id\n" +
            "JOIN tag_gift_certificate ON gift_certificate.id = tag_gift_certificate.gift_certificate_id\n" +
            "JOIN tag ON tag_gift_certificate.tag_id = tag.id\n" +
            "JOIN users_orders ON orders.id=users_orders.order_id\n" +
            "WHERE user_id = (\n" +
            "SELECT user_id from orders\n" +
            "JOIN users_orders ON orders.id = users_orders.order_id\n" +
            "GROUP BY user_id\n" +
            "ORDER BY sum(orders.all_cost) desc LIMIT 1)\n" +
            "group BY tag.id\n" +
            "order by count(tag_id) desc limit 1;";

    public static final String UPDATE_TAG_GIFT_CERTIFICATE =
            "INSERT INTO tag_gift_certificate " +
                    "(gift_certificate_id, tag_id) VALUES (?, ?)";

    public static final String DELETE_TAG_GIFT_CERTIFICATE = "DELETE FROM tag_gift_certificate WHERE gift_certificate_id = ?";

    public static final String GET_CERTIFICATE_BY_ID =  "FROM GiftCertificate   WHERE remove = FALSE AND id = :id";

    public static final String DELETE_CERTIFICATE_REQUEST =
            "UPDATE gift_certificate SET remove = TRUE " +
                    "WHERE id = :id AND remove = FALSE";

    public static final String DELETE_USER_REQUEST =
            "UPDATE users SET remove = TRUE " +
                    "WHERE id = :id AND remove = FALSE";


    public static final String GET_USER_BY_ID_REQUEST = "FROM User WHERE remove = FALSE AND id = :id";

    public static final String GET_TAG_BY_ID_REQUEST = "FROM Tag WHERE remove = FALSE AND id = :id";

    public static final String DELETE_TAG_BY_ID_REQUEST = "UPDATE tag SET remove = TRUE WHERE id = :id AND remove = FALSE";

    public static final String DELETE_ORDER_BY_ID_REQUEST = "Delete from orders WHERE id =:id";

    public static final String GET_LIST_TAG_BY_CERTIFICATE_ID =
            "SELECT tag.id,tag.name, tag.remove " +
                    "FROM tag JOIN tag_gift_certificate ON tag.id = tag_gift_certificate.tag_id " +
                    "JOIN gift_certificate ON tag_gift_certificate.gift_certificate_id = gift_certificate.id " +
                    "WHERE tag_gift_certificate.gift_certificate_id = :gift_certificate_id " +
                    "AND tag.remove = FALSE AND gift_certificate.remove = FALSE";


    //Entity Table
    public static final String CERTIFICATE_DESCRIPTION = "description";
    public static final String CERTIFICATE_CREATE_DATE = "createDate";
    public static final String CERTIFICATE_UPDATE_DATE = "updateDate";
    public static final String CERTIFICATE_PRICE = "price";
    public static final String CERTIFICATE_DURATION = "duration";
    public static final String TAGS_NAME = "tags";
    public static final String REGEX = "%";
    public static final String USER_UPDATE_EXCEPTION = "For now User Entity not support update";
    public static final String UPDATE = "Update";


    //Builder
    public static final String LIMIT_PARAM = "limit";
    public static final String REMOVED = "remove";
    public static final String START_POSITION = "startPosition";
    public static final String SORT = "sort";
    public static final String SORT_ASC = "asc";
    public static final String ENTITY_ID = "id";
    public static final String ENTITY_NAME = "name";
    public static final String USER_LOGIN = "login";
    public static final String USER_EMAIL = "email";
    public static final String PAGE = "page";
    public static final String USER_ENTITY = "user";
    public static final String CERTIFICATE_ID = "gift_certificate_id";


}
