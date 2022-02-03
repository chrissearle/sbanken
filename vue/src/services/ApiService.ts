import http from '@/api-client'

class ApiService {
  getAccounts(): Promise<any> {
    return http.get('/')
  }

  getTransactions(id: string): Promise<any> {
    return http.get(`/${id}`)
  }
}

export default new ApiService()
