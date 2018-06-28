function invokeCommandLink() {
                        var jsfCommandLink = document.getElementById("form:link");
                        jsfCommandLink.click();
                    }
                    var aplikacija = "/" + document.location.pathname.split("/")[1];
                    var wsUri = "ws://" + document.location.host + aplikacija + "/endpoint";


                    var websocket = new WebSocket(wsUri);

                    websocket.onopen = function (evt) {
                        
                    };
                    websocket.onmessage = function (evt) {
                        alert("Stigla poruka");
                        invokeCommandLink();
                    };

