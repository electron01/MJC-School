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

    public static final String DB_DEVELOPMENT_PROPERTIES_FILE = "/dbDevelopment.properties";

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

    public static final String GET_CERTIFICATE_BY_ID = SELECT_ALL_COLUMNS_FROM_GIFT_CERTIFICATE_TABLE +
            FROM_GIFT_CERTIFICATE +
            "WHERE gift_certificate_id = ? AND remove = FALSE ";

    public static final String GET_CERTIFICATE_BY_NAME = SELECT_ALL_COLUMNS_FROM_GIFT_CERTIFICATE_TABLE +
            FROM_GIFT_CERTIFICATE +
            "WHERE name = ? AND remove = FALSE ";
    public static final String DELETE_CERTIFICATE_REQUEST =
            "UPDATE gift_certificate set remove = TRUE " +
                    "WHERE gift_certificate_id = ?";
    public static final String FIND_ALL_TAG_REQUEST = "SELECT tag_id, name FROM tag WHERE remove=FALSE LIMIT ?, ?";

    public static final String SAVE_TAG_REQUEST = "INSERT INTO tag (name) VALUES(?)";

    public static final String GET_TAG_BY_ID_REQUEST = "SELECT tag_id,name FROM tag WHERE tag_id = ? AND remove=FALSE";
    public static final String DELETE_TAG_BY_ID_REQUEST = "UPDATE tag SET remove = TRUE WHERE tag_id = ? AND remove = FALSE";
    public static final String GET_TAG_NY_NAME = "SELECT tag_id, name FROM tag WHERE name = ? AND remove=FALSE";
    public static final String GET_LIST_TAG_BY_CERTIFICATE_ID =
            "SELECT tag.tag_id,tag.name " +
                    "FROM tag JOIN tag_gift_certificate ON tag.tag_id = tag_gift_certificate.tag_id " +
                    "JOIN gift_certificate ON tag_gift_certificate.gift_certificate_id = gift_certificate.gift_certificate_id " +
                    "WHERE tag_gift_certificate.gift_certificate_id = ? " +
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
    public static final String CERTIFICATE_NAME = "gift_certificate.name ";
    public static final String CERTIFICATE_DESCRIPTION = "gift_certificate.description ";
    public static final String CERTIFICATE_CREATE_DATE = "gift_certificate.create_date ";
    public static final String CERTIFICATE_PRICE = "gift_certificate.price";
    public static final String CERTIFICATE_DURATION = "gift_certificate.duration";
    public static final String CERTIFICATE_ID = "gift_certificate_id";
    public static final String TAG_NAME = "tag.name ";
    public static final String AND = " AND ";
    public static final String ORDER_BY = " ORDER BY ";
    public static final String LIMIT = " LIMIT ?,?";
    public static final String ORDER_SEPARATOR = ",";
    public static final String REGEX = "%";
    public static final String EXCEPTED_VALUE = " ?";
    public static final String COMMA = ", ";
    public static final String EQUALLY = " =";
    public static final String START_PART_UPDATE_CERTIFICATE_REQUEST = "UPDATE gift_certificate SET last_update_date = now(), ";
}
