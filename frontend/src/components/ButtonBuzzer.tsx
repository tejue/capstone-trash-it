import styled from "styled-components";

type ButtonBuzzerProps = {
    handleClick?: () => void,
    buttonText?: string,
    position?: string;
}

export default function ButtonBuzzer(props: Readonly<ButtonBuzzerProps>) {
    return (
        <ButtonContainer position={props.position}>
            <BuzzerButton type={"button"} onClick={props.handleClick}>
                <StyledSpan>{props.buttonText}</StyledSpan>
            </BuzzerButton>
        </ButtonContainer>
    )
}

const ButtonContainer = styled.div<ButtonBuzzerProps>`
  display: flex;
  //justify-content: flex-end;
  justify-content: ${({position}) => position ?? 'flex-end'};
  margin: 40px 30px;
  position: fixed;
  bottom: 0;
  //right: 0;
`

const BuzzerButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100px;
  width: 100px;
  overflow: hidden;
  border-radius: 50%;
  border: 1px solid #1f1e1e;
  //background: linear-gradient(0deg, #1a1919, #3b3a3a);
  background: linear-gradient(0deg, #560018, #9f0225);
  box-shadow: 0 20px 30px rgba(0, 0, 0, 0.9);
  transition: box-shadow 0.2s;

  &:hover {
    box-shadow: 0 2px 5px rgb(0, 0, 0, 0.3);
  }
`

const StyledSpan = styled.span`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 80px;
  width: 80px;
  //border: 1px solid #1f1e1e;
  border: 1px solid #810123;
  border-radius: 50%;
  //background: linear-gradient(180deg, #1a1919, #3b3a3a);
  background: linear-gradient(180deg, #560018, #9f0225);
  font-size: 16px;
  color: #c0bdbd;
`