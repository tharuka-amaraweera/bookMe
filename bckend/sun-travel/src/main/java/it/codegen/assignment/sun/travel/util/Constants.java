package it.codegen.assignment.sun.travel.util;

import it.codegen.assignment.sun.travel.dto.WebErrorDTO;

/**
 * The type Constants.
 */
public class Constants {
    /**
     * The constant MILLISECONDSINDAY.
     */
    public static final long MILLISECONDSINDAY = 24*60*60*1000;

    /**
     * The type Error code.
     */
    public class ErrorCode{
        /**
         * The constant ERRORCODE_CG_01.
         */
        public static final String ERRORCODE_CG_01 = "ERORCODE_CG_01";
        /**
         * The constant ERRORCODE_CG_INTERNAL_SERVER_ERROR.
         */
        public static final String ERRORCODE_CG_INTERNAL_SERVER_ERROR = "ERORCODE_CG_02_INTERNAL_SERVER_ERROR";

        /**
         * The constant ERRORCODE_CG_03.
         */
        public static final String ERRORCODE_CG_03 = "ERORCODE_CG_3";

        /**
         * The constant ERRORCODE_CG_04.
         */
        public static final String ERRORCODE_CG_04 = "ERORCODE_CG_4";
        /**
         * The constant ERRORCODE_CG_05.
         */
        public static final String ERRORCODE_CG_05 = "ERRORCODE_CG_05";
        /**
         * The constant ERRORCODE_CG_06.
         */
        public static final String ERRORCODE_CG_06 = "ERRORCODE_CG_06";
        /**
         * The constant ERRORCODE_CG_07.
         */
        public static final String ERRORCODE_CG_07 = "ERRORCODE_CG_07";
        /**
         * The constant ERRORCODE_CG_08.
         */
        public static final String ERRORCODE_CG_08 = "ERRORCODE_CG_08";
        /**
         * The constant ERRORCODE_CG_09.
         */
        public static final String ERRORCODE_CG_09 = "ERRORCODE_CG_09";
        /**
         * The constant ERRORCODE_CG_10.
         */
        public static final String ERRORCODE_CG_10 = "ERRORCODE_CG_10";
    }

    /**
     * The type Error message.
     */
    public class ErrorMessage{
        /**
         * The constant SEARCH_RESERVATION_EMPTY_VALUES.
         */
        public static final String SEARCH_RESERVATION_EMPTY_VALUES = "checkin date and number of nights should be included";
        /**
         * The constant INTERNAL_SERVER_ERROR.
         */
        public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

        /**
         * The constant ROOM_RESERVATION_VIOLATION.
         */
        public static final String ROOM_RESERVATION_VIOLATION = "The provided room id is not associated with the provided reservation id";

        /**
         * The constant INPUT_DATA_VIOLATION.
         */
        public static final String INPUT_DATA_VIOLATION = "All variables in the request payload should be accurate";
        /**
         * The constant DELETE_RESERVATION_VIOLATION.
         */
        public static final String DELETE_RESERVATION_VIOLATION = "There is no reservation for the given reservation Id";
        /**
         * The constant NO_HOTEL_FOUND_ERROR.
         */
        public static final String NO_HOTEL_FOUND_ERROR = "Cannot find a hotel for the provided id";
        /**
         * The constant EMPTY_HOTEL_LIST.
         */
        public static final String EMPTY_HOTEL_LIST ="there is no hotel to be added";
        /**
         * The constant INVALID_HOTEL_ID.
         */
        public static final String INVALID_HOTEL_ID = "Provided hotel id id not valid";
        /**
         * The constant HOTEL_DATA_VIOLATION.
         */
        public static final String DB_DATA_VIOLATION = "provided value is not a valid in the database";
        /**
         * The constant PAYLOAD_TRANSFORMATION_ERROR.
         */
        public static final String PAYLOAD_TRANSFORMATION_ERROR = "Error code in payload transformation";
        /**
         * The constant NO_CONTRACT_FOUND.
         */
        public static final String NO_CONTRACT_FOUND = "No contract was found for the given contract Id";
    }
}
