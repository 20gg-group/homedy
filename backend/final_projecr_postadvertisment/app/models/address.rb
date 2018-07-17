class Address < ApplicationRecord
	validates :full_name, presence: true
	validates :district, presence: true
	validates :presence :true	
end
