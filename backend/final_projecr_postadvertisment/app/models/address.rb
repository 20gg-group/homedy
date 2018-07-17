class Address < ApplicationRecord
	belongs_to :advertisment, required: false	

	validates :city, presence: true
	validates :district, presence: true
	validates :house_no, presence: true	
end
