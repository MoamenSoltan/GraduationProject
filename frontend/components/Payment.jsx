import React from "react";
import { Link } from "react-router-dom";
import { FaCreditCard } from "react-icons/fa"; // Alternative Payment Icon

const Payment = () => {
  return (
    <div className="flex flex-col items-center w-full bg-white  shadow-lg rounded-xl p-6 mt-[3vh] relative overflow-hidden">
      {/* Gradient Header */}
      <div className="absolute top-0 left-0 w-full h-2 bg-gradient-to-r from-green-400 to-blue-500"></div>

      {/* Section Title */}
      <h2 className="text-xl font-semibold flex items-center gap-2 text-gray-700 mt-2">
        <FaCreditCard className="text-blue-500 text-2xl" />
        Payment
      </h2>

      {/* Payment Info */}
      <p className="text-gray-500 text-sm mt-2 text-center">
        Securely manage your payments and outstanding balances.
      </p>

      {/* Payment Button */}
      <Link
        to="/studentDashboard/payment"
        className="bg-blue-500 text-white px-6 py-3 rounded-full text-lg font-semibold shadow-md mt-4 transition-all duration-300 hover:scale-105 hover:bg-blue-600"
      >
        Proceed to Payment
      </Link>
    </div>
  );
};

export default Payment;
