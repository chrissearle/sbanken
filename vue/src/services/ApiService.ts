import http from "@/api-client";

class ApiService {
    getAccounts(): Promise<any> {
        return http.get("/");
    }
}

export default new ApiService();
