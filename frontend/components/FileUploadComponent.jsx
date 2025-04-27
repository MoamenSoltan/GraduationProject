import React, { useState } from 'react';

const FileUploadComponent = () => {
const [file, setFile] = useState(null);
const [responseData, setResponseData] = useState(null);
const API_KEY = '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';

const encodePdfToBase64 = (file) => {
return new Promise((resolve, reject) => {
const reader = new FileReader();
reader.onloadend = () => {
resolve(reader.result.split(',')[1]);  // Extract base64 string
};
reader.onerror = reject;
reader.readAsDataURL(file);
});
};

const handleFileChange = (event) => {
setFile(event.target.files[0]);
};

const handleSubmit = async (event) => {
event.preventDefault();



if (!file) {
  alert('Please upload a PDF file');
  return;
}
try {
  const base64Pdf = await encodePdfToBase64(file);
  const dataUrl = `data:application/pdf;base64,${base64Pdf}`;
  const messages = [
    {
      role: "user",
      content: [
        {
          type: "text",
          text: "What are the main points in this document?",
        },
        {
          type: "file",
          file: {
            filename: file.name,
            file_data: dataUrl,
          },
        },
      ],
    },
  ];
  const plugins = [
    {
      id: "file-parser",
      pdf: {
        engine: "pdf-text", // You can change to "mistral-ocr" if needed
      },
    },
  ];
  const payload = {
    model: "google/gemma-3-27b-it",
    messages: messages,
    plugins: plugins,
  };
  const response = await fetch('https://openrouter.ai/api/v1/chat/completions', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${API_KEY}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(payload),
  });
  const result = await response.json();
  setResponseData(result);  // Store the response data in state
} catch (error) {
  console.error('Error:', error);
}
};

return (
<div>
<h1>Upload PDF</h1>
<form onSubmit={handleSubmit}>
<input type="file" accept="application/pdf" onChange={handleFileChange} />
<button type="submit">Submit</button>
</form>



  {responseData && (
    <div>
      <h2>Response</h2>
      <pre>{JSON.stringify(responseData, null, 2)}</pre>
    </div>
  )}
</div>
);
};

export default FileUploadComponent;