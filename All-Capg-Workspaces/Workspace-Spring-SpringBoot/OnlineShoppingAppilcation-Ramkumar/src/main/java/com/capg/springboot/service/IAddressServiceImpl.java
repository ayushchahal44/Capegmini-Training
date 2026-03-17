package com.capg.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capg.springboot.Exceptions.AddressNotFoundException;
import com.capg.springboot.entity.Address;
import com.capg.springboot.repository.IAddressRepository;

/*/*****************************************************************************
 * IAddress ServiceImpl Class
 * 
 * Created By: 
 * Date:
 *******************************************************************************/

@Service
@Transactional
public class IAddressServiceImpl implements IAddressService{

	@Autowired 
	private IAddressRepository iaddressrepository;
	
	/* Method: addAddress 
	 * Description: It adds the address of the user.
	 * @Override: It indicates that the child class method is over-writing its base class method.
     * return: It returns the address. 
     */

	@Override
	public Address addAddress(Address add) {
		
		return iaddressrepository.save(add);
		
	}
	
//*********************************************************************************************************************	
	
	/* Method: removeAddress 
	 * Description: It remove the address of the user.
	 * @Override: It indicates that the child class method is over-writing its base class method.
     * return: It returns the address. 
     */
	
	@Override
	public Address removeAddress(Address add) {
		
		iaddressrepository.delete(add);
	return add;
	}
	
//*********************************************************************************************************************	
	
	/* Method: updateAddress 
	 * Description: It updates the address of the user.
	 * @Override: It indicates that the child class method is over-writing its base class method.
     * return: It returns the address. 
     */
	
	@Override
	public Address updateAddress(Address add) {
		iaddressrepository.save(add);
		return add;
	}
	
//*********************************************************************************************************************	
	
	/* Method: viewAddressbyId 
	 * Description: It views the address of the user by id.
	 * @Override: It indicates that the child class method is over-writing its base class method.
     * return: It returns the address. 
     */
	
	@Override
	public Address viewAddressBy(String id) throws AddressNotFoundException {
		Address add=iaddressrepository.findByAddressId(id);
            if(add==null)
            	throw new AddressNotFoundException("Address NotFound ");
        return add;
	}

//*********************************************************************************************************************	
	
	/* Method: viewAllAddress 
	 * Description: It views all the address of the user.
	 * @Override: It indicates that the child class method is over-writing its base class method.
     * return: It returns the address. 
     */
	
	@Override
	public List<Address> viewAllAddress() throws AddressNotFoundException  {
		List<Address> address_view = iaddressrepository.findAll();
		return address_view;
	}

}
