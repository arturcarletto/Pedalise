<template>

  <!--Nav bar-->

  <v-app-bar app dense>
    <v-container class="pa-0 ma-0 justify-center align-center" style="max-width: 100%">
      <v-row class="justify-center align-center pl-5 pr-5">
        <v-col>
          <v-toolbar-title>

            <!-- Logo and home link -->

            <router-link to="/">
              <v-img src="../assets/pedalise-logo.png" contain height="50" width="150" alt="Pedalise Logo"
                     class="logo-image"></v-img>
            </router-link>
          </v-toolbar-title>

        </v-col>

        <v-spacer></v-spacer>

        <v-col cols="auto">
          <v-row no-gutters class="justify-space-between align-center">

            <!-- Navigation links -->

            <v-col cols="auto">
              <v-btn to="/">Home</v-btn>
            </v-col>
            <v-col cols="auto">
              <v-btn to="/agenda">Agenda</v-btn>
            </v-col>
            <v-col>
              <v-btn style="width: 100%"
                     variant="flat"
                     :prepend-icon="userService.checkIfIsLogged() ? 'mdi-logout' : 'mdi-login'"
                     :text="userService.checkIfIsLogged() ? 'Logout' : 'log-in'"
                     @click="userService.checkIfIsLogged() ? logout() : loginIsVisible = true">
              </v-btn>
            </v-col>
            <v-col cols="auto">
              <v-list>
                <v-list-item
                  :prepend-avatar="userService.checkIfIsLogged() ? 'https://randomuser.me/api/portraits/men/85.jpg' : ''"
                  :prepend-icon="userService.checkIfIsLogged() ? '' : 'mdi-account'"
                  :title="userData.username">

                </v-list-item>
              </v-list>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </v-app-bar>

  <!--Login Dialog-->

  <v-dialog
    v-model="loginIsVisible"
    style="width: 500px; aspect-ratio: 2/5;">
    <v-card class="pa-6">
      <v-form>
        <v-card-title class="justify-center align-center text-center">
          <p style="font-size: 2.5ch">Login</p>
          <v-label style="font-size: 1.5ch">Use your e-mail address to log in to our site</v-label>
        </v-card-title>
        <v-spacer></v-spacer>
        <v-card-text>
          <v-text-field v-model="formData.email"
                        label="Email"
                        prepend-inner-icon="mdi-email"></v-text-field>

          <v-text-field v-model="formData.password"
                        label="Password"
                        :type="formData.passwordHide ? 'password' : 'text'"
                        prepend-inner-icon="mdi-key"
                        :append-inner-icon="formData.passwordHide ? 'mdi-eye-off' : 'mdi-eye'"
                        @click:append-inner="formData.passwordHide = !formData.passwordHide"></v-text-field>
        </v-card-text>
        <v-card-actions class="text-center align-center flex-column">
          <v-row style="width: 100%">
            <v-col>
              <v-btn @click="loginSubmit" variant="flat" color="blue" style="width: 50%">Log in</v-btn>
            </v-col>
          </v-row>
          <v-row style="width: 100%">
            <v-col>
              <v-label @click="loginIsVisible = false; signinIsVisible = true" class="text-decoration-underline">
                Don't have an account yet?
              </v-label>
            </v-col>
          </v-row>
        </v-card-actions>
      </v-form>
    </v-card>
  </v-dialog>

  <!--Sign in Dialog-->

  <v-dialog
    v-model="signinIsVisible"
    style="width: 500px; aspect-ratio: 2/5;">
    <v-card class="pa-6">
      <v-form>
        <v-card-title class="justify-center align-center text-center">
          <p style="font-size: 2.5ch">Sign up</p>
          <v-label style="font-size: 1.2ch">Create your account with your data</v-label>
        </v-card-title>
        <v-spacer></v-spacer>
        <v-card-text>
          <v-text-field v-model="formData.username"
                        label="Username"
                        placeholder="John doe"
                        prepend-inner-icon="mdi-account"
                        :rules="usernameRules">
          </v-text-field>
          <v-text-field v-model="formData.email"
                        label="E-mail"
                        placeholder="example@gmail.com"
                        prepend-inner-icon="mdi-email"
                        :rules="emailRules"></v-text-field>
          <v-text-field v-model="formData.password"
                        label="Password"
                        placeholder="123456"
                        :type="formData.passwordHide ? 'password' : 'text'"
                        prepend-inner-icon="mdi-key"
                        :append-inner-icon="formData.passwordHide ? 'mdi-eye-off' : 'mdi-eye'"
                        :rules="passwordRules"
                        @click:append-inner="formData.passwordHide = !formData.passwordHide">
          </v-text-field>
          <v-text-field v-model="formData.confirmPassword"
                        label="Confirm your password"
                        placeholder="123456"
                        :type="formData.confirmPasswordHide ? 'password' : 'text'"
                        prepend-inner-icon="mdi-key"
                        :append-inner-icon="formData.confirmPasswordHide ? 'mdi-eye-off' : 'mdi-eye'"
                        :rules="confirmPasswordRules"
                        @click:append-inner="formData.confirmPasswordHide = !formData.confirmPasswordHide">
          </v-text-field>
        </v-card-text>
        <v-card-actions class="text-center align-center flex-column">
          <v-row style="width: 100%">
            <v-col>
              <v-btn @click="signinSubmit" variant="flat" color="blue" style="width: 50%">Sign up</v-btn>
            </v-col>
          </v-row>
          <v-row style="width: 100%">
            <v-col>
              <v-label @click="loginIsVisible = true; signinIsVisible = false" class="text-decoration-underline">
                already have an account?
              </v-label>
            </v-col>
          </v-row>
        </v-card-actions>
      </v-form>
    </v-card>
  </v-dialog>
</template>

<script setup>
import {ref, reactive, onMounted} from "vue";
import httpService from "@/api/HttpService";
import authenticationService from "@/api/AuthenticationService";
import userService from "@/api/UserService";

const loginIsVisible = ref(false)
const signinIsVisible = ref(false)

const formData = reactive({
  username: '',
  email: '',
  password: '',
  confirmPassword: '',
  passwordHide: true,
  confirmPasswordHide: true
})

const userData = reactive({
  username: 'Anonymous',
});

const emailRules = ref([
  (value) => !!value || 'Email address is obligatory',
  (value) => {
    const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    return pattern.test(value) || 'Invalid e-mail.'
  }
])

const usernameRules = ref([
  (value) => !!value || 'Username is obligatory',
  (value) => (value || '').length >= 6 || 'Your username must have more than 6 characters',
  (value) => (value || '').length <= 20 || 'Your username must have less than 20 characters'
])

const passwordRules = ref([
  (value) => {
    if (/[0-9]/.test(value)) {
      return true
    } else return 'Your password must have at least one number'
  },
  (value) => (value || '').length >= 6 || 'Your password must have more then 6 characters',
  (value) => {
    if (/[a-zA-Z]/.test(value)) {
      return true
    } else return 'Your password must have at least one letter'
  }
])

const confirmPasswordRules = ref([
  (value) => {
    if (value === formData.password) {
      return true
    } else return 'Mismatched passwords'
  }
])

function loginSubmit() {
  console.log(formData);

  if (authenticationService.authenticate(formData.email, formData.password)) {
    httpService.get(`api/v1/users/email/${formData.email}`)
      .then(response => {
        console.log(response)
        userData.username = response.data.username;
        localStorage.setItem('email', formData.email)
        userService.setLogged(true)
      })
  }
}

function signinSubmit() {
  console.log(formData);
  httpService.post("/api/v1/users", {
    username: formData.username,
    email: formData.email,
    password: formData.password
  }).then(() => {
    loginSubmit()
  })
}

function logout() {
  console.log(userData)
  userService.setLogged(false)
  localStorage.clear()
  clearUserData();
  clearForms()
  authenticationService.logout();
}

function clearUserData() {
  userData.username = "Anonymous";
  userData.userPicture = '';
}

function clearForms(){
  formData.username = ''
  formData.email = ''
  formData.password =''
  formData.confirmPassword = ''
  formData.passwordHide = true
  formData.confirmPasswordHide = true
}

onMounted(() => {
  let email = localStorage.getItem('email');
  httpService.get(`api/v1/users/email/${email}`)
    .then(response => {
      if(response.data !== ''){
        console.log(response)
        userData.username = response.data.username;
        userService.setLogged(true)
      }else{
        userService.setLogged(false);
        clearUserData()
      }
    })
});

</script>

<style scoped>

.logo-image {

}
</style>
