package com.dinaro.utils;

import android.provider.ContactsContract;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;

public class ValidationUtils {

    private static String EMAIL = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+";

    private static String EMAIL_PATTERN = "[a-zA-Z0-9_-]+@[a-z]{2,50}+\\.+[a-z]{2,3}+";
    private static String EMAIL_PATTERN_With_LIMIT = "[a-zA-Z0-9_-]+@[a-z]{2,50}+\\.+[a-z]{2,3}+\\.+[a-z]{2,3}+";

    public static boolean validEmail(final String email) {
        return email.matches(EMAIL);
       // return email.matches(EMAIL_PATTERN) || email.matches(EMAIL_PATTERN_With_LIMIT) ? true : false;
    }


    public static InputFilter BLOCK_EMOJI = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
                char c = source.charAt(index);
                if (isCharAllowed(c))
                    sb.append(c);
                else
                    keepOriginal = false;
            }
            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }
    };


    public static InputFilter BLOCK_SPACE = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
                char c = source.charAt(index);
                if (isCharAllowedSpace(c))
                    sb.append(c);
                else
                    keepOriginal = false;
            }
            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }
    };

    private static boolean isCharAllowedSpace(char c) {
        return Character.isLetterOrDigit(c) || isSpecialCharEmail(c) ;
    }

    private static boolean isCharAllowed(char c) {
        return Character.isLetterOrDigit(c) || Character.isSpaceChar(c) || isSpecialCharEmail(c) ;
    }
    private static boolean isSpecialCharEmail(char c){
        String specialChar="@#&.-_";
        return specialChar.contains(String.valueOf(c));
    }
}
