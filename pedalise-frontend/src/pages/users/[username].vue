<template>
  <v-sheet style="width: 100%; height:100%">
    <v-row>
      <v-col cols="4">
        <v-sheet color="white" style="width: 100%; height: 100%">

        </v-sheet>
      </v-col>
      <v-col cols="4">
        <v-sheet color="gray" style="width: 100%; height: 100%">

        </v-sheet>
      </v-col>
    </v-row>
  </v-sheet>
</template>

<script setup>
import {onMounted, reactive} from "vue";
import httpService from "@/api/HttpService";
import {useRoute} from "vue-router";

const route = useRoute()

const userData = reactive({
  username: ''
})

onMounted(() => {
  httpService.get(`api/v1/users/username/${route.params.username}`)
    .then(response => {
      console.log(response.username)
      userData.username = response.username;
    })
})
</script>

<style scoped>

</style>
