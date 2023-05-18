/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */
// import 'firebase/auth';
// import { initializeApp  } from 'firebase/app';
import { auth } from 'firebae/auth';

const {onRequest} = require("firebase-functions/v2/https");
const logger = require("firebase-functions/logger");

// Create and deploy your first functions
// https://firebase.google.com/docs/functions/get-started

// exports.helloWorld = onRequest((request, response) => {
//   logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });



const firebaseConfig = {
  apiKey: "AIzaSyBvDdEVi7yz-pK3nNZYuEAbkYmkIMjWdWg",
  authDomain: "lockeriot-9fac5.firebaseapp.com",
  databaseURL: "https://lockeriot-9fac5-default-rtdb.firebaseio.com",
  projectId: "lockeriot-9fac5",
  storageBucket: "lockeriot-9fac5.appspot.com",
  messagingSenderId: "143381588386",
  appId: "1:143381588386:web:1147ad8363dd7af6662afb",
  measurementId: "G-9MSFH9SGM5"
};

const app = initializeApp(firebaseConfig);
const auth = app.auth();
export { auth, db };



const Sing_Form = document.querySelector('#sing-form')

 Sing_Form.addEventListener('submit', (e) => {
   e.preventDefault();
   const wifi= document.querySelector('#name_wifi').value;
   const ps_wifi = document.querySelector('#ps_wifi').value;
   const user_pws = document.querySelector('#email_ps').value;
   const user_email = document.querySelector('#in_email').value;
   const btn_submit = document.querySelector('#btn_submit').value;

   console.log(wifi , ps_wifi , user_email , user_pws);
})


createUserWithEmailAndPassword(auth, user_email, user_pws)
  .then((userCredential) => {
    // Signed in
    const user = userCredential.user;
    console.log("Me registre con exito");
    // ...
  })
  .catch((error) => {
    console.log("Error en registro");
    const errorCode = error.code;
    const errorMessage = error.message;
    // ..
  });


