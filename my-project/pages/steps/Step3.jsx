import React, { useEffect } from 'react'
import ProgressBar from '../../components/ProgressBar'

const Step3 = () => {
  useEffect(() => {
    // Function to prevent back navigation
    const preventBack = () => {
      window.history.pushState(null, "", window.location.href);
    };

    // Push an initial state
    preventBack();

    // Block the back button
    window.addEventListener("popstate", preventBack);

    return () => {
      window.removeEventListener("popstate", preventBack); // Cleanup on unmount
    };
  }, []);
  return (
    <div>
       <div className='w-full mt-10 flex justify-center'>
       <ProgressBar />
       </div>
    </div>
  )
}

export default Step3