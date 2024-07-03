<template>
  <v-container style="width: 100%; height: 100%; max-width: 100%" class="ma-0">
    <v-btn color="primary" @click="openAddEventDialog">Add Event</v-btn>
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <v-card-title class="text-center">
          <span class="text-h5">Add New Event</span>
        </v-card-title>
        <v-card-text>
          <v-form ref="form">
            <v-text-field v-model="newEvent.title" label="Event title" outlined
                          :rules="[v => !!v || 'Title is required']"></v-text-field>
            <v-row>
              <v-col>
                <v-menu
                  ref="startDateMenu"
                  v-model="startDateMenu"
                  :close-on-content-click="true"
                  transition="scale-transition"
                  offset-y
                >
                  <template v-slot:activator="{ attrs }">
                    <v-text-field
                      v-model="newEvent.startDate"
                      label="Start Date"
                      prepend-inner-icon="mdi-calendar"
                      outlined
                      v-bind="attrs"
                      @click="startDateMenu = true"
                    ></v-text-field>
                  </template>
<!--                  <v-date-picker v-model="newEvent.startDate" @input="startDateMenu = false"></v-date-picker>-->
                </v-menu>
              </v-col>
              <v-col>
                <v-menu
                  ref="startTimeMenu"
                  v-model="startTimeMenu"
                  :close-on-content-click="false"
                  transition="scale-transition"
                  offset-y
                >
                  <template v-slot:activator="{ attrs }">
                    <v-text-field
                      v-model="newEvent.startTime"
                      label="Start Time"
                      prepend-inner-icon="mdi-clock"
                      outlined
                      v-bind="attrs"
                      @click="startTimeMenu = true"
                    ></v-text-field>
                  </template>
<!--                  <v-time-picker v-model="newEvent.startTime" @click:minute="startTimeMenu = false"></v-time-picker>-->
                </v-menu>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-menu
                  ref="endDateMenu"
                  v-model="endDateMenu"
                  :close-on-content-click="true"
                  transition="scale-transition"
                  offset-y
                >
                  <template v-slot:activator="{ attrs }">
                    <v-text-field
                      v-model="newEvent.endDate"
                      label="End Date"
                      prepend-inner-icon="mdi-calendar"
                      outlined
                      v-bind="attrs"
                      @click="endDateMenu = true"
                    ></v-text-field>
                  </template>
<!--                  <v-date-picker v-model="newEvent.endDate" @input="endDateMenu = false"></v-date-picker>-->
                </v-menu>
              </v-col>
              <v-col>
                <v-menu
                  ref="endTimeMenu"
                  v-model="endTimeMenu"
                  :close-on-content-click="true"
                  transition="scale-transition"
                  offset-y
                >
                  <template v-slot:activator="{ attrs }">
                    <v-text-field
                      v-model="newEvent.endTime"
                      label="End Time"
                      prepend-inner-icon="mdi-clock"
                      outlined
                      v-bind="attrs"
                      @click="endTimeMenu = true"
                    ></v-text-field>
                  </template>
<!--                  <v-time-picker v-model="newEvent.endTime" @click:minute="endTimeMenu = false"></v-time-picker>-->
                </v-menu>
              </v-col>
            </v-row>
            <v-text-field v-model="newEvent.content" label="Content" outlined></v-text-field>

            <!-- Color Picker -->
            <v-row>
              <v-col>
                <v-text-field v-model="newEvent.color" label="Event Color" outlined readonly></v-text-field>
              </v-col>
              <v-col>
                <v-btn icon @click="openColorPicker">
                  <v-icon :color="newEvent.color">mdi-palette</v-icon>
                </v-btn>
              </v-col>
            </v-row>

            <!-- Suggested Colors -->
            <v-row>
              <v-col v-for="color in colorOptions" :key="color" class="d-flex justify-center">
                <v-icon
                  :color="color"
                  @click="newEvent.color = color"
                  class="hoverable"
                  style="cursor: pointer;"
                >
                  mdi-circle
                </v-icon>
              </v-col>
            </v-row>
          </v-form>

          <!-- Color Picker Dialog -->
          <v-dialog v-model="colorPickerDialog" persistent max-width="400px">
            <v-card>
              <v-card-title>
                <span class="text-h5">Pick a Color</span>
              </v-card-title>
              <v-card-text>
                <v-color-picker v-model="newEvent.color" flat hide-canvas></v-color-picker>
              </v-card-text>
              <v-card-actions>
                <v-btn text @click="colorPickerDialog = false">Close</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" @click="closeDialog">Cancel</v-btn>
          <v-btn color="blue darken-1" @click="addEvent">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-sheet>
      <v-calendar
        ref="calendar"
        v-model:focus="focus"
        :events="events"
        :type="type"
        color="primary"
        :event-color="getEventColor"
        :event-text-color="getEventTextColor"
      ></v-calendar>
    </v-sheet>
  </v-container>
</template>

<script>
import HttpService from '@/api/HttpService';
import {format, parseISO} from 'date-fns';
import authenticationService from "@/api/AuthenticationService";
import userService from "@/api/UserService";

export default {
  data: () => ({
    events: [],
    newEvent: {
      title: '',
      startDate: format(new Date(), 'yyyy-MM-dd'),
      startTime: format(new Date(), 'HH:mm'),
      endDate: format(new Date(), 'yyyy-MM-dd'),
      endTime: format(new Date(), 'HH:mm'),
      content: '',
      color: '#1976D2'
    },
    colorOptions: ['#1976D2', '#E53935', '#8E24AA', '#43A047', '#FB8C00', '#FDD835'],
    dialog: false,
    colorPickerDialog: false,
    startDateMenu: false,
    startTimeMenu: false,
    endDateMenu: false,
    endTimeMenu: false,
    type: 'month',
    types: ['month', 'week', 'day'],
    weekday: [0, 1, 2, 3, 4, 5, 6],
    weekdays: [
      {title: 'Sun - Sat', value: [0, 1, 2, 3, 4, 5, 6]},
      {title: 'Mon - Sun', value: [1, 2, 3, 4, 5, 6, 0]},
      {title: 'Mon - Fri', value: [1, 2, 3, 4, 5]},
      {title: 'Mon, Wed, Fri', value: [1, 3, 5]},
    ],
    focus: format(new Date(), 'yyyy-MM-dd')
  }),
  mounted() {
    this.fetchEvents();
  },
  methods: {
    userService() {
      return userService
    },
    authenticationService() {
      return authenticationService
    },
    fetchEvents() {
      HttpService.get('http://localhost:8080/api/v1/events')
        .then(response => {
          this.events = response.data.map(event => {
            event.start = new Date(event.startDate);
            event.end = new Date(event.endDate);
            return event;
          }).filter(event => event !== null);
        })
        .catch(error => {
          console.error('Error fetching events:', error);
        });
    },
    addEvent() {
      this.$refs.form.validate();
      if (this.newEvent.title && this.newEvent.startDate && this.newEvent.startTime && this.newEvent.endDate && this.newEvent.endTime) {
        const formatString = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

        const startDateTime = format(new Date(`${this.newEvent.startDate}T${this.newEvent.startTime}:00`), formatString);
        const endDateTime = format(new Date(`${this.newEvent.endDate}T${this.newEvent.endTime}:00`), formatString);

        const newEvent = {
          title: this.newEvent.title,
          startDate: startDateTime,
          endDate: endDateTime,
          content: this.newEvent.content,
          color: this.newEvent.color
        };

        console.log('Formatted Start Date and Time:', newEvent.startDate);
        console.log('Formatted End Date and Time:', newEvent.endDate);

        this.events.push({
          title: newEvent.title,
          start: newEvent.startDate,
          end: newEvent.endDate,
          color: newEvent.color,
          timed: true
        });

        HttpService.post('/api/v1/events', newEvent)
          .then(response => {
            console.log('Event added:', response.data);
            this.resetForm();
            this.closeDialog();
          })
          .catch(error => {
            console.error('Error adding event:', error);
          });
      } else {
        alert("Title, start date, start time, end date, and end time are required.");
      }
    },
    resetForm() {
      this.newEvent.title = '';
      this.newEvent.startDate = format(new Date(), 'yyyy-MM-dd');
      this.newEvent.startTime = format(new Date(), 'HH:mm');
      this.newEvent.endDate = format(new Date(), 'yyyy-MM-dd');
      this.newEvent.endTime = format(new Date(), 'HH:mm');
      this.newEvent.content = '';
      this.newEvent.color = '#1976D2';
    },
    openAddEventDialog() {
      this.dialog = true;
    },
    closeDialog() {
      this.dialog = false;
    },
    openColorPicker() {
      this.colorPickerDialog = true;
    },
    getEventColor(event) {
      return event.color || '#1976D2';
    },
    getEventTextColor(event) {
      return '#FFFFFF';
    }
  }
};
</script>

<style scoped>
.hoverable {
  transition: all 0.2s;
}

.hoverable:hover {
  transform: scale(1.2);
}
</style>
