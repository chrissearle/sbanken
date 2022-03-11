import axios, { AxiosInstance } from 'axios'

import Account from '@/types/Account'
import AccountsList from '@/types/AccountsList'
import TransactionList from '@/types/TransactionsList'

const client: AxiosInstance = axios.create({
  baseURL: '/accounts',
  headers: {
    'Content-type': 'application/json',
  },
})

class ApiService {
  getAccounts(): Promise<AccountsList> {
    return client.get<AccountsList>('/').then((response) => response.data)
  }

  getAccount(id: string): Promise<Account> {
    return client.get<Account>(`/${id}`).then((response) => response.data)
  }

  getTransactions(id: string): Promise<TransactionList> {
    return client
      .get<TransactionList>(`/${id}/transactions`)
      .then((response) => response.data)
  }

  postTransfer(
    from: string,
    to: string,
    amount: number,
    message: string
  ): Promise<string> {
    return client
      .post<string>('/transfer', {
        fromAccountId: from,
        toAccountId: to,
        amount: amount,
        message: message,
      })
      .then((response) => response.data)
  }
}

export default new ApiService()
