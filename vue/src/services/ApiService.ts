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
}

export default new ApiService()
