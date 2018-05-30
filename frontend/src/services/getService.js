const getService = {
    getQuote(image) {
        fetch("http://localhost:8080/quote", {
            method: 'GET',
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(function (response) {
                console.log("quote retrieved!")
            })
            .catch(function (err) {
                console.log("quote not generated - I wonder, is this due to technical problems, or my own failings as a person?" + err)
            });
    }
}
export default getService;