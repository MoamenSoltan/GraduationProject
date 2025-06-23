import { useState } from 'react';
import toast from 'react-hot-toast';
import useAxiosPrivate from '../../../hooks/useAxiosPrivate';


const ImageToText = () => {
  const [file, setFile] = useState(null);
  const [responseText, setResponseText] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const axiosPrivate = useAxiosPrivate()

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
    setResponseText('');
  };

  const handleSubmit = async () => {
    if (!file) return toast.error('Please select a file');
    setIsLoading(true);
    setResponseText('');
    try {
      const formData = new FormData();
      formData.append('image', file);
      const response = await axiosPrivate.post('/analyze', formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      });
      setResponseText(response.data?.text || 'No answer received.');
    } catch {
      toast.error('Error analyzing file');
      setResponseText('');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="max-w-xl mx-auto p-6 bg-white rounded-xl shadow-md border border-gray-200">
      <h2 className="text-2xl font-semibold text-center text-gray-700 mb-4">
        Upload Image
      </h2>
      <input
        type="file"
        onChange={handleFileChange}
        className="block w-full px-4 py-2 mb-4 text-sm text-gray-700 bg-gray-50 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
      />
      <button
        onClick={handleSubmit}
        disabled={isLoading}
        className="w-full py-2 px-4 text-white bg-blue-500 rounded-md hover:bg-blue-600 disabled:bg-gray-400 transition-colors"
      >
        {isLoading ? (
          <div className="w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin mx-auto" />
        ) : (
          'Analyze File'
        )}
      </button>
      {responseText && (
        <div className="mt-6 p-6 bg-gray-100 rounded-md border border-gray-300">
          <h3 className="text-xl font-semibold text-gray-700 mb-2">AI Answer:</h3>
          <p className="text-lg text-gray-800 whitespace-pre-wrap">{responseText}</p>
        </div>
      )}
    </div>
  );
};

export default ImageToText;
