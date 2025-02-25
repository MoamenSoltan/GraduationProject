import React from 'react';

const CapacityMeter = ({ enrolled, max }) => {
    // progress bar concept :
    // make a percentage , and 2 nested divs , use the percentage for the inner div , and get the color through a function 


  const getColor = () => {
    if (enrolled >= max * 0.75) return 'bg-red-500';
    if (enrolled >= max * 0.5) return 'bg-yellow-400';
    return 'bg-green-500';
  };

  const percentage = Math.min((enrolled / max) * 100, 100); // Ensure it doesn't exceed 100%

  return (
    <div className="flex items-center justify-center space-x-2 w-32">
        {/* space-x-2 like gap-2 */}
      {/* Enrolled Count */}
      <span className="text-gray-700 font-medium w-6">{enrolled}</span>

      {/* Progress Bar */}
      <div className="w-20 h-3 bg-gray-300  overflow-hidden relative">
        <div
          className={`h-full ${getColor()} transition-all duration-500`}
          style={{ width: `${percentage}%` }} // Dynamic width
          aria-label={`Capacity: ${enrolled} out of ${max}`}
        ></div>
      </div>

      {/* Max Capacity */}
      <span className="text-gray-700 font-medium w-6">{max}</span>
    </div>
  );
};

export default CapacityMeter;
