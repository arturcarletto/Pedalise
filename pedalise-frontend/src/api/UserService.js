import {reactive} from "vue";

let userStatus = reactive({
  logged: false
});

class UserService{

  checkIfIsLogged(){
    return userStatus.logged;
  }

  setLogged(value){
    userStatus.logged = value;
  }
}

export default new UserService()
