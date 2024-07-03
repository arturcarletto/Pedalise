import httpService from "@/api/HttpService";

class UserService{

  async fetchUser() {
    var email = localStorage.getItem('email')
    return httpService.get(`api/v1/users/email/${email}`)
      .then(response => {
        return response.data;
      })
      .catch(reason => {
        console.error(reason)
        return undefined;
      })
  }

}

export default new UserService()
