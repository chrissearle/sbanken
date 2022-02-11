import Transaction from '@/types/Transaction'
import TransactionSum from '@/types/TransactionSum'

export const split = <T>(arr: T[], chunkSize: number): (T[] | null)[] => {
  return arr
    .map((e, i) => {
      return i % chunkSize === 0 ? arr.slice(i, i + chunkSize) : null
    })
    .filter((e) => {
      return e
    })
}

const formatter = new Intl.NumberFormat('no-NO', {
  style: 'currency',
  currency: 'NOK',
})

export const amount = (val: number): string => {
  return formatter.format(val)
}

const dateKey = (date: String): String => {
  return date.substring(0, 10)
}

export const sumTransactionsByDay = (
  transactions: Transaction[]
): TransactionSum[] => {
  return transactions.reduce((finalArray, newDate) => {
    const dateStr = dateKey(newDate.accountingDate)

    const existingDate = finalArray.find((date) => {
      return dateKey(date.accountingDate) === dateStr
    })

    if (existingDate) {
      const existingDateIndex = finalArray.findIndex((date) => {
        dateKey(date.accountingDate) === dateStr
      })

      finalArray.splice(existingDateIndex, 1, {
        ...existingDate,
        amount: existingDate.amount + newDate.amount,
      })

      return finalArray
    }

    return [
      ...finalArray,
      {
        accountingDate: dateStr,
        amount: newDate.amount,
      } as TransactionSum,
    ]
  }, [] as TransactionSum[])
}

export const balanceTransactionsByDay = (
  transactionSums: number[],
  finalBalance: number
): number[] => {
  var balance = 0
  const balanced = transactionSums.map((amount) => {
    balance = balance + amount

    return balance
  })

  const offset = finalBalance - balanced[balanced.length - 1]

  return balanced.map((tx) => {
    return tx + offset
  })
}
