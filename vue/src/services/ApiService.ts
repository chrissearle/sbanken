import http from '@/api-client'

class ApiService {
  getAccounts(): Promise<any> {
    return http.get('/')
  }

  getAccount(id: string): Promise<any> {
    return http.get(`/${id}`)
  }

  getTransactions(id: string): Promise<any> {
    return http.get(`/${id}/transactions`)
  }

  postTransfer(
    from: string,
    to: string,
    amount: number,
    message: string
  ): Promise<any> {
    return http.post('/transfer', {
      fromAccountId: from,
      toAccountId: to,
      amount: amount,
      message: message,
    })
  }
}

export default new ApiService()
