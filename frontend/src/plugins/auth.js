export default {
  $isAuthenticated: false,
  $userRole: '',
  $getUserAuthenticate: async function () {
    const header = {
      method: 'GET',
    };
    await fetch(`${URL}/api/check`, header)
      .then(res => res.json()
        .then(json => {
          this.$isAuthenticated = true;
          this.$userRole = typeof json === 'string' || myVar instanceof String ? json : json.authorities[0].authority;
        }))
      .catch(error => {
        console.error(error);
        this.$isAuthenticated = false;
      });
  },
  $userLogin: async function (credentials) {
    const header = {
      method: 'GET',
      headers: {
      'Content-Type': 'application/json;charset=utf-8',
      'Accept': 'application/json'
      },
      body: JSON.stringify(credentials),
    };
    return fetch(`${URL}/api/login`, header)
      .then(res => res.json()
      .then(json => {
        this.$isAuthenticated = true;
        this.$userRole = typeof json === 'string' || myVar instanceof String ? json : json.authorities[0].authority;
      }))
    .catch(error => {
      console.error(error);
      this.$isAuthenticated = false;
    });
  },
  $userLogout: async function() {
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/logout`, header)
      .then((res) => {
        this.$isAuthenticated = false;
      })
      .catch(error => console.error(error));
  },
};