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
          <v-text-field v-model="newEvent.start" label="Start Date" type="date" outlined></v-text-field>
          <v-text-field v-model="newEvent.end" label="End Date" type="date" outlined></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="closeDialog">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="addEvent">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
  <div>
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
        label="weekdays"
        variant="outlined"
        dense
        hide-details
      ></v-select>
    <v-sheet>
      <v-calendar
        ref="calendar"
        v-model="value"
        :events="events"
        :view-mode="type"
        :weekdays="weekday"
      ></v-calendar>
    </v-sheet>
  </div>
</template>

<script>
import { useDate } from 'vuetify'
import httpService from '@/api/HttpService'
import axios from 'axios'

const loading = ref(false)

const events = ref([])

const fetchEvents = async () => {
  httpService.get('api/v1/events/').then(response => {
    loading.value = true
    events.value = response.data;
    loading.value = false
  }).catch(error => {
    console.error('Erro ao buscar eventos:', error);
    loading.value = false;
  });
};

onMounted(fetchEvents)

export default {
  data: () => ({
    type: 'month',
    types: ['month', 'week', 'day'],
    weekday: [0, 1, 2, 3, 4, 5, 6],
    weekdays: [
      { title: 'Sun - Sat', value: [0, 1, 2, 3, 4, 5, 6] },
      { title: 'Mon - Sun', value: [1, 2, 3, 4, 5, 6, 0] },
      { title: 'Mon - Fri', value: [1, 2, 3, 4, 5] },
      { title: 'Mon, Wed, Fri', value: [1, 3, 5] },
    ],
    events: [],
    colors: [
      'blue',
      'indigo',
      'deep-purple',
      'cyan',
      'green',
      'orange',
      'grey darken-1',
    ],
    titles: [
      'Meeting',
      'Holiday',
      'PTO',
      'Travel',
      'Event',
      'Birthday',
      'Conference',
      'Party',
    ],
    dialog: false,
    value: new Date(),
    newEvent: {
      name: '',
      start: '',
      end: ''
    }
  }),
  mounted() {
    const adapter = useDate()
    this.getEvents({
      start: adapter.startOfDay(adapter.startOfMonth(new Date())),
      end: adapter.endOfDay(adapter.endOfMonth(new Date())),
    })
  },
  methods: {
    getEvents({ start, end }) {
      const events = []

      const min = start
      const max = end
      const days = (max.getTime() - min.getTime()) / 86400000
      const eventCount = this.rnd(days, days + 20)

      for (let i = 0; i < eventCount; i++) {
        const allDay = this.rnd(0, 3) === 0
        const firstTimestamp = this.rnd(min.getTime(), max.getTime())
        const first = new Date(firstTimestamp - (firstTimestamp % 900000))
        const secondTimestamp = this.rnd(2, allDay ? 288 : 8) * 900000
        const second = new Date(first.getTime() + secondTimestamp)

        events.push({
          title: this.titles[this.rnd(0, this.titles.length - 1)],
          start: first,
          end: second,
          color: this.colors[this.rnd(0, this.colors.length - 1)],
          allDay: !allDay,
        })
      }

      this.events = events
    },
    getEventColor(event) {
      return event.color
    },
    rnd(a, b) {
      return Math.floor((b - a + 1) * Math.random()) + a
    },
    openAddEventDialog() {
      this.dialog = true;
    },
    closeDialog() {
      this.dialog = false;
    },
    addEvent() {
      const eventToAdd = {
        ...this.newEvent,
        color: 'blue' // default color or could be selected by the user
      };
      this.events.push(eventToAdd);
      this.saveEvent(eventToAdd);
      this.closeDialog();
      this.resetNewEvent();
    },
    resetNewEvent() {
      this.newEvent = { name: '', start: '', end: '' };
    },
    saveEvent(event) {
      // API call to save the event
      axios.post('/api/events', event)
        .then(response => {
          // Handle response here
          console.log('Event added:', response.data);
        })
        .catch(error => {
          console.error('There was an error adding the event:', error);
        });
    }

  },
}
</script>
