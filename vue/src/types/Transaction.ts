interface CardDetails {
  cardNumber?: string
  currencyAmount: number
  currencyRate: number
  merchantCategoryCode?: string
  merchantCategoryDescription?: string
  merchantCity?: string
  merchantName?: string
  originalCurrencyCode?: string
  purchaseDate: string
  transactionId: string
}

interface TransactionDetail {
  formattedAccountNumber?: string
  transactionId: number
  cid: string
  amountDescription: string
  receiverName?: string
  numericReference: number
  payerName?: string
  registrationDate?: string
}

export default interface Transaction {
  accountingDate: string
  interestDate?: string
  otherAccountNumber?: string
  otherAccountNumberSpecified: boolean
  amount: number
  text?: string
  transactionType?: string
  transactionTypeCode: number
  transactionTypeText: string
  isReservation: boolean
  reservationType?: string
  source?: string
  cardDetailsSpecified: boolean
  cardDetails?: CardDetails
  transactionDetailSpecified: boolean
  transactionDetail?: TransactionDetail
}
