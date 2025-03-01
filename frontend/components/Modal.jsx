import React, { useEffect } from 'react'
import ReactDom from 'react-dom'
/**
 * 
 * modal notes : 
 * make the component and and the props are (open , onCLose , children)
 * open is a state that opens the modal when true (visible bg-black/20)
 * Onclose setes it to false 
 * children are the content of the modal
 * 
 * stop propagation to make the inner div , when clicked not affected by onCLose
 * 
 * announcementscard for implementation of modal 
 * 
 * In Tailwind CSS, inset-0 is a shorthand utility that applies top: 0; right: 0; bottom: 0; left: 0;.
 * 
 * use a portal to make the modal visible on top level of DOM
 * React Portals allow rendering a component outside its current DOM hierarchy, ensuring that fixed positioning works as expected. 
 * 
 * a portal takes to parameters , the second is the document.getElementById of modal-root
 * 
 * add modal-root in index.html 
 * 
 * prevent scrolling using useEffect

It's useful for:
✅ Positioning an element to cover its parent completely.
✅ Making an element fill a container when using absolute or fixed positioning.
inner div transition for popup animation
 */

const Modal = ({open,onClose,children}) => {
    useEffect(() => {
        if (open) {
          document.body.classList.add("overflow-hidden");
        } else {
          document.body.classList.remove("overflow-hidden");
        }
    
        return () => {
          document.body.classList.remove("overflow-hidden"); // Cleanup when modal unmounts
        };
      }, [open]);
    if (!open) return null; // Prevent rendering when modal is closed

  return ReactDom.createPortal(
    <div onClick={onClose} className={`inset-0 fixed cursor-default  flex justify-center items-center  transition-colors ${open ? "visible bg-black/20":"invisible"}`}>
        {/* since parent of this is body , and we have inset-0 , bg-black will be applied to body */}

        <div onClick={(e)=>{e.stopPropagation()}} className={`bg-white max-w-[90%] overflow-hidden rounded-xl  shadow p-6 transition-all ${open ?"scale-100 opacity-100" :"scale-125 opacity-0"}`}>
            {children} {/* children prop contains the content of modal */}
            <button onClick={onClose} className="absolute top-4 right-4 text-gray-600 hover:text-gray-800">X</button> {/* close button */}
        </div>

        
    </div> ,
    document.getElementById('portal') // here  is the id of the div in index.html where the modal will be rendered
  )
}

export default Modal