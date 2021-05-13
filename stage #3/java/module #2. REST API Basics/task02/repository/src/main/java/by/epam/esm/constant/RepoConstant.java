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
    public static final String REMOVED = "remove";
    public static final String SELECT_ALL_COLUMNS_FROM_GIFT_CERTIFICATE_TABLE =
            "SELECT gift_certificate_id,name," +
                    "description, price, duration, create_date, last_update_date ";
    public static final String FROM_GIFT_CERTIFICATE = "FROM gift_certificate ";
    public static final String UPDATE_TAG_GIFT_CERTIFICATE =
            "INSERT INTO tag_gift_certificate " +
                    "(gift_certificate_id, tag_id) VALUES (?, ?)";
    public static final String DELETE_TAG_GIFT_CERTIFICATE = "DELETE FROM tag_gift_certificate WHERE gift_certificate_id = ?";
    public static final String SAVE_CERTIFICATE_REQUEST =
            "INSERT INTO  gift_certificate" +
                    " (name,description,price,duration) VALUES (?,?,?,?)";

    public static final String GET_CERTIFICATE_BY_ID =  "FROM GiftCertificate   WHERE remove = FALSE AND id = :id";

    public static final String GET_CERTIFICATE_BY_NAME = SELECT_ALL_COLUMNS_FROM_GIFT_CERTIFICATE_TABLE +
            FROM_GIFT_CERTIFICATE +
            "WHERE name = ? AND remove = FALSE ";
    public static final String DELETE_CERTIFICATE_REQUEST =
            "UPDATE gift_certificate SET remove = TRUE " +
                    "WHERE gift_certificate_id = :id AND remove = FALSE";
    public static final String FIND_ALL_TAG_REQUEST ="FROM tag WHERE remove=FALSE";

    public static final String SAVE_TAG_REQUEST = "INSERT INTO tag (name) VALUES(?)";

    public static final String GET_TAG_BY_ID_REQUEST = "FROM Tag WHERE remove = FALSE AND id = :id";
    public static final String DELETE_TAG_BY_ID_REQUEST = "UPDATE tag SET remove = TRUE WHERE id = :id AND remove = FALSE";
    public static final String GET_TAG_NY_NAME = "SELECT tag_id, name FROM tag WHERE name = ? AND remove=FALSE";
    public static final String GET_LIST_TAG_BY_CERTIFICATE_ID =
            "SELECT tag.id,tag.name, tag.remove " +
                    "FROM tag JOIN tag_gift_certificate ON tag.id = tag_gift_certificate.tag_id " +
                    "JOIN gift_certificate ON tag_gift_certificate.gift_certificate_id = gift_certificate.id " +
                    "WHERE tag_gift_certificate.gift_certificate_id = :gift_certificate_id " +
                    "AND tag.remove = FALSE AND gift_certificate.remove = FALSE";

    public static final String FULL_SELECT = "SELECT DISTINCT gift_certificate.gift_certificate_id, gift_certificate.name," +
            "gift_certificate.description, gift_certificate.price, gift_certificate.duration," +
            "gift_certificate.create_date, gift_certificate.last_update_date " +
            "FROM gift_certificate " +
            "LEFT JOIN tag_gift_certificate " +
            "ON gift_certificate.gift_certificate_id = tag_gift_certificate.gift_certificate_id " +
            "LEFT JOIN tag ON tag_gift_certificate.tag_id = tag.tag_id " +
            "WHERE gift_certificate.remove = FALSE AND tag.remove = FALSE ";

    public static final String WHERE = "WHERE ";
    public static final String LIKE = "LIKE ?";
    public static final String CERTIFICATE_DESCRIPTION = "description";
    public static final String CERTIFICATE_CREATE_DATE = "createDate";
    public static final String CERTIFICATE_UPDATE_DATE = "updateDate";
    public static final String CERTIFICATE_PRICE = "price";
    public static final String CERTIFICATE_DURATION = "duration";
    public static final String TAGS_NAME = "tags";
    public static final String AND = " AND ";
    public static final String ORDER_BY = " ORDER BY ";
    public static final String LIMIT = " LIMIT ?,?";
    public static final String ORDER_SEPARATOR = ",";
    public static final String REGEX = "%";
    public static final String EXCEPTED_VALUE = " ?";
    public static final String COMMA = ", ";
    public static final String EQUALLY = " =";
    public static final String START_PART_UPDATE_CERTIFICATE_REQUEST = "UPDATE gift_certificate SET last_update_date = now(), ";


    //Builder
    public static final String LIMIT_PARAM = "limit";
    public static final String START_POSITION = "startPosition";
    public static final String SORT = "sort";
    public static final String SORT_ASC = "asc";
    public static final String ENTITY_ID = "id";
    public static final String ENTITY_NAME = "name";
    public static final String PAGE = "page";

}
