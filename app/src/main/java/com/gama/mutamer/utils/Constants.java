package com.gama.mutamer.utils;

import com.gama.mutamer.R;
import com.gama.mutamer.viewModels.prayTimes.Method;
import com.gama.mutamer.viewModels.prayTimes.Rounding;
import com.gama.mutamer.viewModels.shared.Language;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by mustafa on 5/15/18.
 * Release the GEEK
 */
public class Constants {


    /***
     * Constants Data
     */
    public static final ArrayList<Language> APP_LANGUAGES = new ArrayList<Language>(
            Arrays.asList(
                    new Language(R.string.english, R.drawable.ic_en),
                    new Language(R.string.arabic, R.drawable.ic_ar)
            ));

    public final static String SELECTED_FILTER_FLAG = "selectedFilter";

    public static final int Notification_FILTER_FLAG = 1, UNITS_FILTER_FLAG = 6;
    public static final String LAST_FRAGMENT_ID = "lastFragmentId";


    public static final String ID = "Id";
    public static final String TITLE = "Title";
    public static final String BODY = "Body";
    public static final String ISREAD = "IsRead";
    public static final String DATE = "Date";
    public static final String CREATED_DATE = "CreatedDate";//CreatedDate
    public static final String LAST_UPDATE_DATE = "LastUpdateDate";
    public static final String NUMBERS = "Numbers";
    public static final String NUMBER = "Number";
    public static final String USER_TOKEN = "UserToken";
    public static final String COUNTRIES = "Countries";

    public static final String USER_NAME_FIELD = "userName";
    public static final String PASSWORD_FIELD = "Password";
    public static final String ACCESS_TOKEN_FIELD = "access_token";

    public final static int CAPTURE_IMAGE_FLAG = 0, CAPTURE_VIDEO_FLAG = 1;


    public static final String POI_GENERAL_TYPE_ID = "pid";
    public static final String GALLERY_FOLDER = "Gallery";
    public static final String MAHRIM_ID = "mutamerId";
    public static final String HAJJ_MAHAREMS = "mahramList";
    public static final String PREF_USER_FIRST_TIME = "userSawIntro";
    public static final int FILTER_UNREAD = 0, FILTER_READ = 1, FILTER_ALL = 2;
    /***
     * Flags
     */
    public static final String NOTIFICATION_ITEM = "notification";
    public static final String WEATHER = "weather";
    public static final String MOTAWEF = "motawef";
    public static final String CATEGORY_ID = "CategoryId";
    public static final String ENGLISH_NAME = "EnglishName";
    public static final String ARABIC_NAME = "ArabicName";
    public static final String ENGLISH_BODY = "EnglishBody";
    public static final String ARABIC_BODY = "ArabicBody";
    public static final String TIME = "Time";
    public static final String LATITUDE = "Latitude";
    public static final String LONGITUDE = "Longitude";


    public static final String PRAYER_METHOD = "prayerMethod";
    public static final String TOPIC_GENERAL = "PushGeneral";

    public static final String FIREBASE_MESSAGING_SERVICE = "FCM_MSG";
    public static final String TYPE = "Type";
    public static final int NOTIFICATION_TYPE_TEXT = 0;
    public static final int NOTIFICATION_TYPE_URL = 1;
    public static final int NOTIFICATION_TYPE_IMAGE = 2;
    public static final int STATE_LOADING = 555;
    public static final int STATE_DATA = 666;

    public static final String BOARDER_NUMBER = "boarderNo";
    public static final String VISA_NUMBER = "VisaNumber";
    public static final String NATIONALITY_ID = "NationalityId";
    public static final String NATIONALITY_ARABIC_NAME = "nationalityNameAr";
    public static final String NATIONALITY_ENGLISH_NAME = "nationalityNameLa";
    public static final String FROM_COUNTRY = "fromCountry";
    public static final String AGE = "Age";
    public static final String DATE_OF_BIRTH = "DateOfBirth";

    public static final String PASSPORT_NUMBER = "PassportNumber";


    public static final String PLACES = "Places";


    public static final String NATIONALITY = "Nationality";
    public static final int GET_PILGRIM_PROGRAM = 1001;
    public static final String RESERVATION_NUMBER = "reservationNo";
    public static final String MOBILE_NUMBER = "MobileNumber";
    public static final String IDENTIFICATION_NUMBER = "identificationNo";
    public static final int STATE_NO_DATA = 777;
    public static final String NAME = "Name";
    public static final String IMAGE_NAME = "ImageName";
    public static final String DESCRIPTION = "Description";
    public static final String URL = "Url";
    public static final String APPS = "apps";
    public static final String UNIT_METHOD = "UnitType";
    public static final String WEATHER_UNIT = "WeatherUnit";
    public static final String PRAYER_NOTIFICATION_TYPE = "PrayerNotificationType";
    public static final String ITEMS = "Items";
    public static final String POI_ID = "i";
    public static final String POI_TYPE_ID = "pti";
    public static final String POI_ADDRESS = "address";
    public static final String POI_ENGLISH_NAME = "englishName";
    public static final String POI_ARABIC_NAME = "name";
    public static final String POI_PHONE = "phone";
    public static final String POI_PHONE_2 = "phone2";
    public static final String WEB_SITE = "WebSite";
    public static final String POI_LOCATION = "location";
    public static final String POI_LATITUDE = "lat";
    public static final String POI_LONGITUDE = "lon";
    public static final String BOUNDARY_ID = "BoundaryId";
    public static final int MINA = 1;
    public static final int MUZDALIFAH = 2;
    public static final int ARAFT = 3;
    public static final int MAKKAH = 4;
    public static final int MADINAH = 5;
    public static final String STATUS = "Status";
    public static final String HAS_ATTACHMENTS = "HasAttachments";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    public static final int CAPTURE_IMAGE_REPORT_ACTIVITY_REQUEST_CODE = 300;
    public static final int CAPTURE_VIDEO_REPORT_ACTIVITY_REQUEST_CODE = 400;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final String KEY_LATITUDE = "location_latitude";
    public static final String KEY_LONGITUDE = "location_longitude";
    public static final String[][] CALCULATION_METHOD_COUNTRY_CODES = new String[][]{

            /** METHOD_EGYPT_SURVEY:    Africa, Syria, Iraq, Lebanon, Malaysia, Parts of the USA **/
            new String[]{
                    // Africa
                    "AGO", "BDI", "BEN", "BFA", "BWA", "CAF", "CIV", "CMR", "COG", "COM", "CPV", "DJI", "DZA", "EGY", "ERI", "ESH", "ETH", "GAB", "GHA", "GIN", "GMB", "GNB", "GNQ", "KEN", "LBR", "LBY", "LSO", "MAR", "MDG", "MLI", "MOZ", "MRT", "MUS", "MWI", "MYT", "NAM", "NER", "NGA", "REU", "RWA", "SDN", "SEN", "SLE", "SOM", "STP", "SWZ", "SYC", "TCD", "TGO", "TUN", "TZA", "UGA", "ZAF", "ZAR", "ZWB", "ZWE",
                    // Syria, Iraq, Lebanon, Malaysia
                    "IRQ", "LBN", "MYS", "SYR"
            },

            /** METHOD_KARACHI_SHAF:        ____ **/
            new String[]{},

            /** METHOD_KARACHI_HANAF:   Pakistan, Bangladesh, India, Afghanistan, Parts of Europe **/
            new String[]{"AFG", "BGD", "IND", "PAK"},

            /** METHOD_NORTH_AMERICA:   Parts of the USA, Canada, Parts of the UK **/
            new String[]{"USA", "CAN"},

            /** METHOD_MUSLIM_LEAGUE:   Europe, The Far East, Parts of the USA **/
            new String[]{
                    // Europe
                    "AND", "AUT", "BEL", "DNK", "FIN", "FRA", "DEU", "GIB", "IRL", "ITA", "LIE", "LUX", "MCO", "NLD", "NOR", "PRT", "SMR", "ESP", "SWE", "CHE", "GBR", "VAT",
                    // Far East
                    "CHN", "JPN", "KOR", "PRK", "TWN"
            },

            /** METHOD_UMM_ALQURRA:     The Arabian Peninsula **/
            new String[]{"BHR", "KWT", "OMN", "QAT", "SAU", "YEM"},

            /** METHOD_FIXED_ISHAA:     ___ **/
            new String[]{}

    };
    public static final Method[] CALCULATION_METHODS = new Method[]{Method.UMM_ALQURRA, Method.MUSLIM_LEAGUE, Method.NORTH_AMERICA, Method.EGYPT_SURVEY};
    public static final short DEFAULT_CALCULATION_METHOD = 0; // UMM_ALQURRA
    public static final int FAJR = 0, SUNRISE = 1, DHUHR = 2, ASR = 3, MAGHRIB = 4, ISHAA = 5, NEXT_FAJR = 6; // Notification Times
    public static final short NOTIFICATION_NONE = 0, NOTIFICATION_PLAY = 1;//, NOTIFICATION_CUSTOM = 3; // Notification Methods
    public static final Rounding[] ROUNDING_METHODS = new Rounding[]{Rounding.NONE, Rounding.NORMAL, Rounding.SPECIAL, Rounding.AGRESSIVE};
    public static final short DEFAULT_ROUNDING_INDEX = 2; // Special
    public static final String EXTRA_ACTUAL_TIME = "uz.efir.muazzin.EXTRA_ACTUAL_TIME";
    public static final String EXTRA_TIME_INDEX = "uz.efir.muazzin.EXTRA_TIME_INDEX";
    /***
     * Sync Service Tag
     */
    public static final String SYNC_SERVICE_TAG = "SyncService";
    /***
     * Notifications Service Tag
     */
    public static final String NOTIFICATIONS_SERVICE_TAG = "NotificationsService";
    /***
     * Notification Service
     */
    public static final String NOTIFICATION_SERVICE_ACTION_NOTIFY = "com.hajj.manasikna.NOTIFICATION_SERVICE_ACTION_NOTIFY";
    public static final String NOTIFICATION_SERVICE_EXTRA_NOTIFICATION_ID = "com.hajj.manasikna.EXTRA_NOTIFICATION_ID";
    public static final String LANGUAGE = "Language";
    public static final String FRAGMENT_DATA = "fragmentData";
    public static final String DUAA = "Duaa";
    public static final String SOS = "Sos";
    public static final String NOTIFICATIONS = "Notifications";
    public static final String ISSUES = "Issues";
    public static final String IMPORTANT_NUMBERS = "ImportantNumberCategories";
    public static final int LOGIN_SUCCESS = 102;
    public static final int CODE_ADD_ISSUE = 103;
    public static final int NETWORRD_TAG = 1985;
    public static final String CODE = "Code";
    public static final String GENDER = "Gender";
    public static final String BLOOD_TYPE = "BloodType";
    public static final String MOFA_NUMBER = "MofaNumber";
    public static final String COMPANY = "Company";
    public static final String AGENT = "Agent";
    public static final String NEW_LINE = "\n";
    public static final String DISPLAY_NAME = "DisplayName";
    public static final String TAG = "MutamerApp";
    public static final String DUAA_CATEGORIES = "DuaaCategories";
    public static final String PLACES_CATEGORIES = "PlacesCategories";
    public static final String NUMBERS_CATEGORIES = "NumbersCategories";
    public static final String SLIDERS = "Sliders";
    public static final String PATH = "Path";
    public static final String PILGRIM = "Mutamer";
    public static final String MOI_NUMBER = "MoiNumber";
    public static final String PHONE_NUMBER = "Phone";
    public static final String EMAIL_ADDRESS = "Email";
    public static final String CONTACT_PERSON = "PersonName";
    public static final String UMRAH_COMPANY = "Company";
    public static final String ADDRESS = "Address";
    public static final String HOTELS = "Hotels";
    public static final String HOTEL_DETAILS = "Name";
    public static final String START_DATE = "StartDate";
    public static final String END_DATE = "EndDate";
    public static final String CITY_ID = "CityId";
    public static final String CITY_ARABIC_NAME = "CityArabicName";
    public static final String CITY_ENGLISH_NAME = "CityEnglishName";
    public static final String TRANSPORTATION = "Transportation";
    public static final String FROM = "From";
    public static final String TO = "To";
    public static final String VEHICLE = "Vehicle";
    public static final String PLATE_NUMBER = "PlateNumber";
    public static final String DRIVER = "Driver";
    public static final String DEPARTUTE = "Departure";
    public static final String FROM_CITY = "FromCity";
    public static final String TO_CITY = "ToCity";
    public static final String AIR_LINE = "AirLine";
    public static final String CARRIER = "Carrier";
    public static final String TERMINAL = "Terminal";
    public static final String ARRIVAL = "Arrival";
    public static final String CITY = "City";
    public static final String COUNTRY_ID = "CountryId";
    public final static int FROM_CURRENCY_FLAG = 0, TO_CURRENCY_FLAG = 1;
    public static final String CURRENCY_CODE = "CurrencyCode";
    public static final String PLAYER_POSITION = "playerPosition";
    public static final String BROARD_CAST_DATA_CHANGE = "DataChanged";
    public static final String MUTAMER_PROFILE_FETCHED = "MutamerProfileFetched";
    public static final String ICON = "icon";
    public static final String USER_LOGGED_OFF = "UserLoggedOff";
    public static final String IS_DELETED = "Deleted";
    public static String[] currencyCodes = new String[]{"AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL", "BSD", "BTC", "BTN", "BWP", "BYN", "BZD", "CAD", "CDF", "CHF", "CLF", "CLP", "CNH", "CNY", "COP", "CRC", "CUC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "GBP", "GEL", "GGP", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS", "IMP", "INR", "IQD", "IRR", "ISK", "JEP", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KMF", "KPW", "KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LYD", "MAD", "MDL", "MGA", "MKD", "MMK", "MNT", "MOP", "MRO", "MRU", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLL", "SOS", "SRD", "SSP", "STD", "STN", "SVC", "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TWD", "TZS", "UAH", "UGX", "USD", "UYU", "UZS", "VEF", "VES", "VND", "VUV", "WST", "XAF", "XAG", "XAU", "XCD", "XDR", "XOF", "XPD", "XPF", "XPT", "YER", "ZAR", "ZMW", "ZWL",
    };

    static final float GEOFENCE_RADIUS_IN_METERS = 1609; // 1 mile, 1.6 km
    /**
     * Map for storing information about airports in the San Francisco bay area.
     */
    static final HashMap<String, LatLng> BAY_AREA_LANDMARKS = new HashMap<>();
    private static final String PACKAGE_NAME = "com.google.android.gms.location.Geofence";
    static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";
    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
    /**
     * For this sample, geofences expire after twelve hours.
     */
    static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    public static final String USER_CHOOSE_LANGUAGE = "userChooseLanguage";

    public static final String DUAA_ALARM_NOTIFICATION = "duaaAlarmNotification";
    public static int[] icons = new int[]{R.drawable.ic_hospital, R.drawable.ic_hotels, R.drawable.ic_services2, R.drawable.ic_rest, R.drawable.ic_banking, R.drawable.ic_mall};
    public static double makkahLat = 21.422487;
    public static double makkahLng = 39.826190;


    /***
     * Log Tags
     */
    public static double myCurLat = 21.422487;
    public static double myCurLng = 39.826190;
    public static int[] TIME_NAMES = new int[]{R.string.fajr, R.string.sunrise, R.string.dhuhr, R.string.asr, R.string.maghrib, R.string.ishaa, R.string.next_fajr};

    static {
        // San Francisco International Airport.
        BAY_AREA_LANDMARKS.put("SFO", new LatLng(37.621313, -122.378955));

        // Googleplex.
        BAY_AREA_LANDMARKS.put("GOOGLE", new LatLng(37.422611, -122.0840577));
    }

    static public double finals(double lat1, double long1, double lat2,
                                double long2)

    {

        return (_bearing(lat2, long2, lat1, long1) + 180.0) % 360;

    }

    public enum FilterType {
        Unread,
        Read,
        All
    }

    static private double _bearing(double lat1, double long1, double lat2,
                                   double long2)

    {

        double degToRad = Math.PI / 180.0;

        double phi1 = lat1 * degToRad;

        double phi2 = lat2 * degToRad;

        double lam1 = long1 * degToRad;

        double lam2 = long2 * degToRad;

        return Math.atan2(Math.sin(lam2 - lam1) * Math.cos(phi2),

                Math.cos(phi1) * Math.sin(phi2) - Math.sin(phi1) * Math.cos(phi2)
                        * Math.cos(lam2 - lam1)

        )
                * 180 / Math.PI;

    }


}
