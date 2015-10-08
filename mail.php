<?php

$uri = 'https://mandrillapp.com/api/1.0/messages/send.json';

$email_file = file_get_contents("file_name");
$email_code= base64_encode($email_file);


$postString = '{
"key": "your_key",
"message": {
    
    "html": "html body",
    "subject": "subject",
    "from_email": "from_email",
    "from_name": "from_name",
	 "attachments": [
    {
        "type": "file_type",
        "name": "file_name",
        "content": "'.$email_code.'"
    }
],
    "to": [
        {
            "email": "to_email",
            "name": "to_name"
        }
    ]
},
"async": false
}';

$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, $uri);
curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true );
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true );
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
curl_setopt($ch, CURLOPT_POST, true);
curl_setopt($ch, CURLOPT_POSTFIELDS, $postString);

$result = curl_exec($ch);

echo $result;

?>