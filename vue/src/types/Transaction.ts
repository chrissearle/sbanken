interface CardDetails {
  cardNumber: string | null;
  currencyAmount: number;
  currencyRate: number;
  merchantCategoryCode: string | null;
  merchantCategoryDescription: string | null;
  merchantCity: string | null;
  merchantName: string | null;
  originalCurrencyCode: string | null;
  purchaseDate: string;
  transactionId: string;
}

interface TransactionDetail {
  formattedAccountNumber: string | null;
  transactionId: number;
  cid: string;
  amountDescription: string;
  receiverName: string | null;
  numericReference: number;
  payerName: string | null;
  registrationDate: string | null;
}

export default interface Transaction {
  accountingDate: string;
  interestDate: string | null;
  otherAccountNumber: string | null;
  otherAccountNumberSpecified: boolean;
  amount: number;
  text: string | null;
  transactionType: string | null;
  transactionTypeCode: number;
  transactionTypeText: string;
  isReservation: boolean;
  reservationType: string | null;
  source: string | null;
  cardDetailsSpecified: boolean;
  cardDetails: CardDetails | null;
  transactionDetailSpecified: boolean;
  transactionDetail: TransactionDetail | null;
}
