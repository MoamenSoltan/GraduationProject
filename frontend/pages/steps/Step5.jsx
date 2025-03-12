import React from 'react'
import ProgressBar from '../../components/ProgressBar'
import { motion } from 'motion/react'
import Check from '../../data/check.svg'
import { useStateContext } from '../../contexts/ContextProvider'
import { useNavigate } from 'react-router'
import axios from '../../api/axios'

const Step5 = () => {
    const { user, setUser } = useStateContext();
    const navigate = useNavigate();

    const handleSubmit = () => {
        const formData = new FormData();
        for (let key in user) {
            formData.append(key, user[key]);
        }

        //TODO: enums in backend require UPPERCASE , in our project , gender is enum 
        // notes :
        // check field names with backend
        const register = async () => {
            try {
              console.log("Submitting User Data:", user);

                const response = await axios.post("/auth/register", formData, {
                    headers: { "Content-Type": "multipart/form-data" },
                    withCredentials: true
                });
                console.log(response.data);
                navigate("/registration/done");
            } catch (error) {
                console.log("An error occurred during registration:", error.response.data);
            }
        };
        register();
    };

    return (
        <motion.div initial={{ x: "-5%" }}
            animate={{ x: 0 }}
            exit={{ x: "-5%" }}
            transition={{ duration: 0.5 }}>
            
            <div className='w-full mt-10 flex justify-center'>
                <ProgressBar />
            </div>

            <div className='md:w-[55%] text-center md:p-0 p-4 mt-[7%] m-auto flex-col flex items-center justify-center'>
                <img src={Check} className='w-[100px] h-[100px]' alt="check" />
                <h1 className='text-center main-text'>You are almost done!</h1>
                <p className='sub-text'>By pressing the button below, you agree to our terms and conditions</p>

                {/* Render Form Data */}
                <div className="w-full mt-4 p-4 border rounded-lg bg-gray-100">
                    <h2 className="text-lg font-semibold mb-2">Your Submitted Data:</h2>
                    <ul className="text-left">
                        {Object.entries(user).map(([key, value]) => (
                            <li key={key} className="mb-2">
                                <strong>{key}:</strong>
                                {value instanceof File ? (
                                    <div className="mt-2">
                                        <img 
                                            src={URL.createObjectURL(value)} 
                                            alt={value.name} 
                                            className="w-32 h-32 object-cover rounded-md border mt-1"
                                        />
                                    </div>
                                ) : value instanceof FileList ? (
                                    <div className="mt-2 flex gap-2">
                                        {Array.from(value).map((file, index) => (
                                            <img 
                                                key={index} 
                                                src={URL.createObjectURL(file)} 
                                                alt={file.name} 
                                                className="w-32 h-32 object-cover rounded-md border"
                                            />
                                        ))}
                                    </div>
                                ) : (
                                    <span className="ml-2">{value.toString()}</span>
                                )}
                            </li>
                        ))}
                    </ul>
                </div>

                <button onClick={handleSubmit} className='custom-button mt-4'>Finish</button>
            </div>
        </motion.div>
    )
}

export default Step5;
