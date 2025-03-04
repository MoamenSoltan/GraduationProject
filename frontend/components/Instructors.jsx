import React from 'react';
import { instructorsData } from '../data/dummy';

const Instructors = () => {
  //TODO: API CALL
  return (
    <div className="flex flex-col w-full bg-white shadow-lg rounded-xl p-6 mt-5 relative overflow-hidden">
      {/* Gradient Header */}
      <div className="absolute top-0 left-0 w-full h-2 bg-gradient-to-r from-purple-500 to-indigo-500"></div>

      {/* Section Title */}
      <h2 className="text-xl font-semibold text-gray-700 mb-4 text-center">
        Top Instructors
      </h2>

      {/* Instructors List with Overlapping Images */}
      <div className="flex justify-center -space-x-4 mt-2">
        {instructorsData.map((instructor, index) => (
          <img
            key={instructor.id}
            src={instructor.image}
            alt={`Instructor ${index + 1}`}
            className="w-16 h-16 rounded-full border-2 border-white shadow-lg transition-transform hover:scale-110"
            style={{ zIndex: instructorsData.length - index }} // Ensures right images are above left ones
          />
        ))}
      </div>

      {/* Subtext */}
      <p className="text-gray-500 text-sm mt-3 text-center">
        Top instructors in your College
      </p>
    </div>
  );
};

export default Instructors;
