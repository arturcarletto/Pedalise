<template>
  <v-app-bar app dense>
    <v-container class="pa-0 ma-0 justify-center align-center" style="max-width: 100%">
      <v-row class="justify-center align-center pl-5 pr-5">
        <v-col>
          <v-toolbar-title>

            <!-- Logo and home link -->

            <router-link to="/">
              <v-img src="../assets/pedalise-logo.png" contain height="50" alt="Pedalise Logo"
                     class="logo-image"></v-img>
            </router-link>
          </v-toolbar-title>

        </v-col>
        <v-spacer></v-spacer>
        <v-col cols="3">
          <v-row no-gutters class="justify-space-between align-center">

            <!-- Navigation links -->

            <v-col  cols="auto">
              <v-btn to="/">Home</v-btn>
            </v-col>
            <v-col  cols="auto">
              <v-btn to="/agenda">Agenda</v-btn>
            </v-col>
            <v-col cols="auto">
              <v-avatar :image="userData.userPicture" @click="drawer = !drawer"></v-avatar>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </v-app-bar>
  <v-navigation-drawer v-model="drawer" location="right">
    <v-list-item :title="userData.username"
                 :prepend-avatar="userData.userPicture"
                 class="v-avatar--rounded pa-4">
    </v-list-item>
    <v-divider></v-divider>
    <v-row class="pl-3 pr-3 pt-6">
      <v-col>
        <v-btn style="width: 100%"
               variant="flat"
               prepend-icon="mdi-account"
               size="large">Minha conta
        </v-btn>
      </v-col>
    </v-row>
    <v-row class="pl-3 pr-3">
      <v-col>
        <v-btn style="width: 100%"
               variant="flat"
               :prepend-icon="logged ? 'mdi-logout' : 'mdi-login'"
               :text="logged ? 'Logout' : 'log-in'"
               size="large"
               @click="logged ? logout : loginIsVisible = true">
        </v-btn>
      </v-col>
    </v-row>
  </v-navigation-drawer>
  <v-dialog
    v-model="loginIsVisible"
    style="width: 500px; aspect-ratio: 2/5;">
    <v-card class="pa-6">
      <v-form>
        <v-card-title class="justify-center align-center text-center">
          <p style="font-size: 2.5ch">Inicie uma Sessão</p>
          <v-label style="font-size: 1.5ch">Use seu email para entrar em sua conta</v-label>
        </v-card-title>
        <v-spacer></v-spacer>
        <v-card-text>
          <v-text-field v-model="formData.email"
                        label="Email"
                        prepend-inner-icon="mdi-email"></v-text-field>

          <v-text-field v-model="formData.password"
                        label="Senha"
                        :type="formData.passwordHide ? 'password' : 'text'"
                        prepend-inner-icon="mdi-key"
                        :append-icon="formData.passwordHide ? 'mdi-eye-off' : 'mdi-eye'"
                        @click:append="formData.passwordHide = !formData.passwordHide"></v-text-field>
        </v-card-text>
        <v-card-actions class="text-center align-center flex-column">
          <v-row style="width: 100%">
            <v-col>
              <v-btn @click="loginSubmit" variant="flat" color="blue" style="width: 50%">ENTRAR</v-btn>
            </v-col>
          </v-row>
          <v-row style="width: 100%">
            <v-col>
              <v-label @click="loginIsVisible = false; signinIsVisible = true" class="text-decoration-underline">Você já
                possui uma conta?
              </v-label>
            </v-col>
          </v-row>
        </v-card-actions>
      </v-form>
    </v-card>
  </v-dialog>
  <v-dialog
    v-model="signinIsVisible"
    style="width: 500px; aspect-ratio: 2/5;">
    <v-card class="pa-6">
      <v-form>
        <v-card-title class="justify-center align-center text-center">
          <p style="font-size: 2.5ch">Crie sua conta Pedalise</p>
          <v-label style="font-size: 1.2ch">insira seus dados corretamente para criar uma conta</v-label>
        </v-card-title>
        <v-spacer></v-spacer>
        <v-card-text>
          <v-text-field v-model="formData.username"
                        label="Nome de usúario"
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
                        label="Senha"
                        placeholder="123456"
                        :type="formData.passwordHide ? 'password' : 'text'"
                        prepend-inner-icon="mdi-key"
                        :append-icon="formData.passwordHide ? 'mdi-eye-off' : 'mdi-eye'"
                        :rules="passwordRules"
                        @click:append="formData.passwordHide = !formData.passwordHide">

          </v-text-field>
          <v-text-field v-model="formData.confirmPassword"
                        label="Confirme sua senha"
                        placeholder="123456"
                        :type="formData.confirmPasswordHide ? 'password' : 'text'"
                        prepend-inner-icon="mdi-key"
                        :append-icon="formData.confirmPasswordHide ? 'mdi-eye-off' : 'mdi-eye'"
                        :rules="confirmPasswordRules"
                        @click:append="formData.confirmPasswordHide = !formData.confirmPasswordHide">

          </v-text-field>
        </v-card-text>
        <v-card-actions class="text-center align-center flex-column">
          <v-row style="width: 100%">
            <v-col>
              <v-btn @click="signinSubmit" variant="flat" color="blue" style="width: 50%">CRIAR</v-btn>
            </v-col>
          </v-row>
          <v-row style="width: 100%">
            <v-col>
              <v-label @click="loginIsVisible = true; signinIsVisible = false" class="text-decoration-underline">
                Ainda não possui uma conta?
              </v-label>
            </v-col>
          </v-row>
        </v-card-actions>
      </v-form>
    </v-card>
  </v-dialog>
</template>

<script setup>
import {ref, reactive} from "vue";
import httpService from "@/api/HttpService";
import authenticationService from "@/api/AuthenticationService";

const logged = ref(false)
const drawer = ref(false)
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
  username: 'Gemaplys',
  userPicture: 'https://randomuser.me/api/portraits/men/85.jpg'
});

const emailRules = ref([
  (value) => !!value || 'Email é obrigatório',
  (value) => {
    const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    return pattern.test(value) || 'Invalid e-mail.'
  }
])

const usernameRules = ref([
  (value) => !!value || 'Nome de usúario é obrigatório',
  (value) => (value || '').length >= 6 || 'Seu nome de usúario precisa ter mais de 6 caracteres',
  (value) => (value || '').length <= 20 || 'Seu nome de usúario precisa ter menos de 20 caracteres'
])

const passwordRules = ref([
  (value) => {
    if (/[0-9]/.test(value)) {
      return true
    } else return 'Sua senha precisa conter pelo menos um número'
  },
  (value) => (value || '').length >= 6 || 'Sua senha precisa ter mais de 6 caracteres',
  (value) => {
    if (/[a-zA-Z]/.test(value)) {
      return true
    } else return 'Sua senha precisa conter pelo menos um caractér'
  }
])

const confirmPasswordRules = ref([
  (value) => {
    if (value === formData.password) {
      return true
    } else return 'Senhas incompatíveis'
  }
])

function loginSubmit() {
  console.log(formData);

  if (authenticationService.authenticate(formData.email,formData.password)) {
    httpService.get(`api/v1/users/email/${formData.email}`)
      .then(response => {
        console.log(response)
        userData.username = response.data.username;
        logged.value = true;
      })
  }
}

function signinSubmit() {
  console.log(formData);
  httpService.post("/api/v1/users", {
    username: formData.username,
    email: formData.email,
    password: formData.password
  })
}

function logout() {
  console.log(userData)
  clearUserData();
  authenticationService.logout();
}

function clearUserData(){
  userData.username = "Anonymous";
  userData.userPicture = 'https://randomuser.me/api/portraits/men/85.jpg';
}

</script>

<style scoped>

.logo-image {
  margin-left: -30%; /* Adjust this value as needed */
}
</style>
