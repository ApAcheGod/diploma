module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors:{
        primary:"#8C452E",
        secondary: "#B1A38F",
        accent: "#C8A170",
        brandwhite: "#D8D4C9",

        accent2 : "#6200EE",
        accent3 : "#03DAC5",
      }
    },
    fontFamily: {
      'montserrat': ['Montserrat'],
    }
  },
  plugins: [],
}
