<template>
  <v-main>
    <h1 class="text-h1">
      HOME
    </h1>
    <v-form>
      <p>Username</p>
      <input v-model="cadastro.username">
      <p>email</p>
      <input type="email" v-model="cadastro.email">
      <p>password</p>
      <input type="password" v-model="cadastro.password"><br>
      <v-btn @click="registerUser()" value="Register">Register</v-btn>
    </v-form>
    <v-btn v-for="user in users" value={{user.username}}>
      <h1>Usuario</h1>
      <p>{{user.id}}</p><br>
      <p>{{user.username}}</p><br>
      <p>{{user.email}}</p><br>
    </v-btn>

  </v-main>

</template>

<script setup>

import {ref} from "vue";
import httpService from "@/api/HttpService";

var cadastro = {
    username: '',
    email: '',
    password: ''
}


const users = ref()
// const events = ref([]);

function registerUser(){
  httpService.post("/api/v1/users", {
    username: cadastro.username,
    email: cadastro.email,
    password: cadastro.password
  }).then(() => fetchUsers())
}

const fetchUsers = async () => {
  httpService.get('api/v1/users').then(response =>{
    users.value = response.value
    console.log(response.value)
  }).catch(error =>{
    console.error("Erro na busca do Usuario: " + error);
  })
}

// const fetchEvents = async () => {
//   httpService.get('api/v1/events/').then(response =>{
//     events.value = response.value
//     console.log(response.value)
//   }).catch(error => {
//     console.error("Erro na busca do Evento: " + error)
//   })
//  }

</script>

<style scoped>

</style>
