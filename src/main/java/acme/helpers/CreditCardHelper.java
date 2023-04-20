
package acme.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import acme.framework.helpers.MomentHelper;

public abstract class CreditCardHelper {

	// Constructors -----------------------------------------------------------

	protected CreditCardHelper() {
	}

	// Validating card number methods --------------------------------------------------------

	public static Boolean hasCorrectCardNumberFormat(final String creditCardNumber) {

		return creditCardNumber != null && !creditCardNumber.isEmpty() && creditCardNumber.matches("^\\d{16}$");
	}

	public static Boolean hasValidCreditNumber(final String creditCardNumber) {

		int totalSum = 0;

		if (CreditCardHelper.hasCorrectCardNumberFormat(creditCardNumber))
			for (final char c : creditCardNumber.toCharArray()) {
				final String strValue = "" + c;
				final Integer value = Integer.parseInt(strValue.trim());
				totalSum += value;
			}

		return totalSum % 10 == 0;
	}

	// Validating expiry date methods --------------------------------------------------------

	public static Boolean hasCorrectExpiryDateFormat(final String expiryDate) {

		return expiryDate != null && !expiryDate.isEmpty() && expiryDate.matches("^([0][1-9]|[1][12])[/][0-9][0-9]$");
	}

	public static Boolean hasValidExpiryDate(final String expiryDate) {

		boolean res = false;

		if (CreditCardHelper.hasCorrectExpiryDateFormat(expiryDate)) {

			final String[] expDateArray = expiryDate.split("/");

			final String month = expDateArray[0].trim();
			final String fullYear = "20" + expDateArray[1];
			final String strCardDate = "31/" + month + "/" + fullYear + " 23:59";

			final Date cardDate;
			final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			try {
				cardDate = formatter.parse(strCardDate);
				final Date currentMoment = MomentHelper.getCurrentMoment();
				res = MomentHelper.isAfter(cardDate, currentMoment);
			} catch (final ParseException e) {
				return false;
			}
		}

		return res;
	}

	// Validating CVV/CVC methods --------------------------------------------------------

	public static Boolean hasCorrectCVVFormat(final String CVV) {

		return CVV != null && !CVV.isEmpty() && CVV.matches("^\\d{3}$");
	}

}
