<template>
  <v-container>
    <v-btn color="primary" @click="openAddEventDialog">Add Event</v-btn>
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="text-h5">Add New Event</span>
        </v-card-title>
        <v-card-text>
          <v-text-field v-model="newEvent.name" label="Event Name" outlined></v-text-field>
          <v-text-field v-model="newEvent.start_date" label="Start Date" type="date" outlined></v-text-field>
          <v-text-field v-model="newEvent.end_date" label="End Date" type="date" outlined></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="closeDialog">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="addEvent">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-select
      v-model="type"
      :items="types"
      class="ma-2"
      label="View Mode"
      variant="outlined"
      dense
      hide-details
    ></v-select>
    <v-select
      v-model="weekday"
      :items="weekdays"
      class="ma-2"
      label="Weekdays"
      variant="outlined"
      dense
      hide-details
    ></v-select>
    <v-sheet>
      <v-calendar
        ref="calendar"
        v-model:focus="focus"
        :events="events"
        :type="type"
        color="primary"
      ></v-calendar>
    </v-sheet>
  </v-container>
</template>

<script>
import HttpService from '@/api/HttpService'

export default {
  data: () => ({
    events: [],
    newEvent: {
      name: '',
      start_date: new Date().toISOString().substr(0, 10),
      end_date: new Date().toISOString().substr(0, 10)
    },
    dialog: false,
    type: 'month',
    types: ['month', 'week', 'day'],
    weekday: [0, 1, 2, 3, 4, 5, 6],
    weekdays: [
      { title: 'Sun - Sat', value: [0, 1, 2, 3, 4, 5, 6] },
      { title: 'Mon - Sun', value: [1, 2, 3, 4, 5, 6, 0] },
      { title: 'Mon - Fri', value: [1, 2, 3, 4, 5] },
      { title: 'Mon, Wed, Fri', value: [1, 3, 5] },
    ],
    focus: new Date().toISOString().substr(0, 10) // Initialize with today's date
  }),
  mounted() {
    this.fetchEvents();
  },
  methods: {
    fetchEvents() {
      HttpService.get('http://localhost:8080/api/v1/events')
        .then(response => {
          console.log('Fetched events:', response.data);
          this.events = response.data.map(event => {
            console.log('Raw event:', event);

            // Validate and parse dates
            if (!event.start_date || !event.end_date) {
              console.error('Event has invalid dates:', event);
              return null;
            }

            try {
              const start = new Date(event.start_date).toISOString();
              const end = new Date(event.end_date).toISOString();
              return {
                name: event.name,
                start: start,
                end: end,
                color: event.color,
              };
            } catch (error) {
              console.error('Error parsing dates:', error);
              return null;
            }
          }).filter(event => event !== null);
        })
        .catch(error => {
          console.error('Error fetching events:', error);
        });
    },
    addEvent() {
      console.log('Start Date:', this.newEvent.start_date);
      console.log('End Date:', this.newEvent.end_date);

      const newEvent = {
        name: this.newEvent.name,
        start_date: new Date(this.newEvent.start_date).toISOString(),
        end_date: new Date(this.newEvent.end_date).toISOString(),
        color: 'blue'
      };

      console.log('Formatted Start Date:', newEvent.start_date);
      console.log('Formatted End Date:', newEvent.end_date);

      // Add the event locally
      this.events.push(newEvent);

      // Post the new event to the server
      HttpService.post('http://localhost:8080/api/v1/events', newEvent)
        .then(response => {
          console.log('Event added:', response.data);
          this.resetForm();
          this.closeDialog();
        })
        .catch(error => {
          console.error('Error adding event:', error);
        });
    },
    resetForm() {
      this.newEvent.name = '';
      this.newEvent.start_date = new Date().toISOString().substr(0, 10);
      this.newEvent.end_date = new Date().toISOString().substr(0, 10);
    },
    openAddEventDialog() {
      this.dialog = true;
    },
    closeDialog() {
      this.dialog = false;
    }
  }
};
</script>
