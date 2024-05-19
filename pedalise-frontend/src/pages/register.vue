<template>
  <v-container>
    <v-container>
      <v-card>
        <v-card-title><span class="text-h3">Register</span></v-card-title>
        <v-card-text>
          <v-card-item>
            <v-text-field v-model="register.username" placeholder="Username" label="Username"></v-text-field>
            <v-text-field type="email" v-model="register.email" placeholder="email" label="Email"></v-text-field>
            <v-text-field type="password" v-model="register.password" placeholder="password" label="password"></v-text-field>
          </v-card-item>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-container class="align-content-space-between">
            <v-btn value="login">Login</v-btn>
            <v-btn color="blue darken-1" @click="registerUser()" value="Register">Register</v-btn>
          </v-container>
        </v-card-actions>
      </v-card>
    </v-container>
  </v-container>
</template>

<script setup>
import httpService from "@/api/HttpService";

let register = {
  username: '',
  email: '',
  password: ''
}

function registerUser(){
  console.log(register);

  httpService.post("/api/v1/users", {
    username: register.username,
    email: register.email,
    password: register.password
  })
    .then(() => fetchUsers())
    .catch(error => {
      console.error("Failed to register user:", error);
    });
}
const fetchUsers = async () => {
  httpService.get('api/v1/users')
    .then(response =>{
      console.log(response)
    }).catch(error =>{
    console.error("Erro na busca do Usuario: " + error);
  })
}
console.log(fetchUsers())
</script>

<style scoped>

</style>
